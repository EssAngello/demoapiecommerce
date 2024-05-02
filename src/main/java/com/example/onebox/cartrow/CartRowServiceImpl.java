package com.example.onebox.cartrow;

import com.example.onebox.audit.AuditService;
import com.example.onebox.cartrow.model.CartRowDto;
import com.example.onebox.cartrow.model.CartRowEntity;
import com.example.onebox.common.exception.EntityNotFoundException;
import com.example.onebox.common.exception.InvalidDataException;
import com.example.onebox.config.mapper.CartRowEntityMapper;
import com.example.onebox.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CartRowServiceImpl implements CartRowService {

    @Autowired
    private AuditService auditService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartRowEntityMapper cartRowEntityMapper;

    private final Map<Long, CartRowEntity> cartRows = new ConcurrentHashMap<>();
    private static final AtomicLong idCounter = new AtomicLong(1);

    /**
     * {@inheritDoc}
     * @throws EntityNotFoundException if the cart row is not found
     */
    @Override
    public CartRowEntity getCartRow(Long id) {
        if(!cartRows.containsKey(id) || cartRows.get(id).getAudit().getAudDeleted())
            throw new EntityNotFoundException("Cart row with id " + id + " not found");

        return cartRows.get(id);
    }

    /**
     * {@inheritDoc}
     * @throws EntityNotFoundException if the cart row is not found
     */
    @Override
    public void delete(Long id) {
        if (!cartRows.containsKey(id) || cartRows.get(id).getAudit().getAudDeleted())
            throw new EntityNotFoundException("Cart row with id " + id + " not found");

        cartRows.get(id).setAudit(auditService.deleteAudit(cartRows.get(id).getAudit()));
    }

    /**
     * {@inheritDoc}
     * @throws InvalidDataException if the cart row data is invalid
     */
    @Override
    public CartRowEntity save(CartRowDto dto) {
        isValidCartRow(dto);

        CartRowEntity cartRow;

        if (dto.getId() != null) {
            cartRow = getCartRow(dto.getId());
            cartRow.setAudit(auditService.editAudit(cartRow.getAudit()));
        } else {
            cartRow = new CartRowEntity();
            cartRow.setId(generateCartRowId());
            cartRow.setAudit(auditService.newAudit());
        }

        cartRow.setCartId(dto.getCartId());
        cartRow.setProduct(productService.getProduct(dto.getProduct().getId()));

        cartRows.put(cartRow.getId(), cartRow);

        return cartRowEntityMapper.partialUpdate(dto, cartRow);
    }

    private void isValidCartRow(CartRowDto dto) {
        if (dto.getCartId() == null)
            throw new InvalidDataException("Cart id is required");

        if (dto.getProduct() == null)
            throw new InvalidDataException("Product is required");

        if (dto.getQuantity() == null || dto.getQuantity() <= 0)
            throw new InvalidDataException("Quantity must be greater than 0");

        if (dto.getQuantity() > dto.getProduct().getQuantity())
            throw new InvalidDataException("Quantity must be less than or equal to product quantity");

    }

    private static Long generateCartRowId() {
        return idCounter.getAndIncrement();
    }

    private Boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

}
