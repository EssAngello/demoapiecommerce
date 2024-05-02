package com.example.onebox.cart;

import com.example.onebox.audit.AuditService;
import com.example.onebox.cart.model.CartDto;
import com.example.onebox.cart.model.CartEntity;
import com.example.onebox.cartrow.CartRowService;
import com.example.onebox.cartrow.model.CartRowEntity;
import com.example.onebox.common.exception.EntityNotFoundException;
import com.example.onebox.common.exception.InvalidDataException;
import com.example.onebox.config.mapper.CartEntityMapper;
import com.example.onebox.customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private AuditService auditService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartRowService cartRowService;

    @Autowired
    private CartEntityMapper cartEntityMapper;

    private final Map<Long, CartEntity> carts = new ConcurrentHashMap<>();
    private static final AtomicLong idCounter = new AtomicLong(1);

    /**
     * {@inheritDoc}
     * @throws EntityNotFoundException if no carts are found
     */
    @Override
    public List<CartEntity> getAllCarts() {
        if(carts.isEmpty())
            throw new EntityNotFoundException("No carts found");

        return carts.values().stream()
                .filter(cart -> !cart.getAudit().getAudDeleted())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     * @throws EntityNotFoundException if no carts are found
     */
    @Override
    public List<CartEntity> getCartsByCustomerId(Long id) {
        if(carts.isEmpty())
            throw new EntityNotFoundException("No carts found");

        return carts.values().stream()
                .filter(cart -> cart.getCustomer().getId().equals(id))
                .filter(cart -> !cart.getAudit().getAudDeleted())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     * @throws EntityNotFoundException if no cart is found
     */
    @Override
    public CartEntity getLastActiveCartByCustomerId(Long id) {
        return carts.values().stream()
                .filter(cart -> cart.getCustomer().getId().equals(id))
                .filter(cart -> !cart.getAudit().getAudDeleted())
                .max((cart1, cart2) -> cart1.getAudit().getAudCreationDate().compareTo(cart2.getAudit().getAudCreationDate()))
                .orElseThrow(() -> new EntityNotFoundException("No cart found"));
    }

    /**
     * {@inheritDoc}
     * @throws EntityNotFoundException if the cart is not found
     */
    @Override
    public void delete(Long id) {
        if (!carts.containsKey(id) || carts.get(id).getAudit().getAudDeleted())
            throw new EntityNotFoundException("Cart with id " + id + " not found");

        CartEntity cart =carts.get(id);
        cart.setAudit(auditService.deleteAudit(carts.get(id).getAudit()));
        cart.getCartRows().forEach(row -> cartRowService.delete(row.getId()));
    }

    /**
     * {@inheritDoc}
     * @throws InvalidDataException if the customer is null
     */
    @Override
    public CartEntity save(CartDto dto) {

        isValidCart(dto);

        CartEntity cart;

        if (dto.getId() != null) {
            cart = getCart(dto.getId());
            cart.setAudit(auditService.editAudit(cart.getAudit()));
        } else {
            cart = new CartEntity();
            cart.setId(generateCartId());
            cart.setAudit(auditService.newAudit());
        }

        cart.setCustomer(customerService.getCustomer(dto.getCustomer().getId()));

        CartEntity finalCart = cart;
        List<CartRowEntity> updatedRows = dto.getCartRows()
                .stream()
                .map(rowDto -> {
                    CartRowEntity rowEntity = cartRowService.save(rowDto);
                    rowEntity.setCartId(finalCart.getId()); // Establecer el carrito explícitamente
                    return rowEntity;
                })
                .collect(Collectors.toList());

        cart.setCartRows(updatedRows);

        carts.put(cart.getId(), cart);

        return cartEntityMapper.partialUpdate(dto, cart);
    }

    /**
     * {@inheritDoc}
     * @throws EntityNotFoundException if the cart is not found
     */
    @Override
    public CartEntity getCart (Long id) {
        if (!this.carts.containsKey(id) || this.carts.get(id).getAudit().getAudDeleted())
            throw new EntityNotFoundException("Cart not found");

        return this.carts.get(id);
    }


    private void isValidCart(CartDto data) {

        if (data.getCustomer() == null)
            throw new InvalidDataException("Customer cannot be null");

    }

    private static long generateCartId() {
        return idCounter.getAndIncrement();
    }

    /**
     * This method is scheduled to run at fixed intervals to remove inactive carts.
     * It is annotated with @Scheduled, which indicates that the method should be scheduled.
     * The fixedRate parameter specifies the interval between method invocations measured from the start time of each invocation.
     * The initialDelay parameter indicates the delay before the first execution of the method.
     *
     * In this method, the current time is first obtained. Then, for each cart in the carts map, the last updated time is retrieved.
     * If the difference between the current time and the last updated time is greater than the specified threshold (600000 milliseconds or 10 minutes),
     * the cart is considered inactive and is removed by calling the delete method with the cart's id.
     */
    @Scheduled(fixedRate = 600000, initialDelay = 600000)
    public void removeInactiveCarts() {
        log.info("Removing inactive carts");
        long currentTime = System.currentTimeMillis();
        carts.forEach((id, cart) -> {
            Timestamp lastUpdated = cart.getAudit().getAudModificationDate();
            if ((currentTime - lastUpdated.getTime()) > 600000) {
                delete(cart.getId());
            }
        });
    }

    private Boolean isNullOrEmpty(String item) {
        return item == null || item.equals("");
    }

}