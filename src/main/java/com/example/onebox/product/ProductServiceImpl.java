package com.example.onebox.product;

import com.example.onebox.audit.AuditService;
import com.example.onebox.product.model.ProductDto;
import com.example.onebox.product.model.ProductEntity;
import com.example.onebox.common.exception.EntityNotFoundException;
import com.example.onebox.common.exception.InvalidDataException;
import com.example.onebox.config.mapper.ProductEntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private AuditService auditService;

    @Autowired
    private ProductEntityMapper productEntityMapper;

    private final Map<Long, ProductEntity> products = new ConcurrentHashMap<>();
    private static final AtomicLong idCounter = new AtomicLong(1);

    /**
     * {@inheritDoc}
     * @throws EntityNotFoundException if the product is not found
     */
    @Override
    public ProductEntity getProduct(Long id) {
        if (!products.containsKey(id) || products.get(id).getAudit().getAudDeleted())
            throw new EntityNotFoundException("Product with id " + id + " not found");

        return products.get(id);
    }

    /**
     * {@inheritDoc}
     * @throws EntityNotFoundException if no products are found
     */
    @Override
    public List<ProductEntity> getAllProducts() {
        if(products.isEmpty())
            throw new EntityNotFoundException("No products found");

        return products.values().stream()
                .filter(product -> !product.getAudit().getAudDeleted())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     * @throws EntityNotFoundException if the product is not found
     */
    @Override
    public void delete(Long id) {
        if (!products.containsKey(id) || products.get(id).getAudit().getAudDeleted())
            throw new EntityNotFoundException("Product with id " + id + " not found");

        products.get(id).setAudit(auditService.deleteAudit(products.get(id).getAudit()));
    }

    /**
     * {@inheritDoc}
     * @throws InvalidDataException if the product data is invalid
     */
    @Override
    public ProductEntity save(ProductDto dto) {

        isValidProduct(dto);

        ProductEntity productEntity;

        if (dto.getId() != null) {
            productEntity = getProduct(dto.getId());
            productEntity.setAudit(auditService.editAudit(productEntity.getAudit()));
        } else {
            productEntity = new ProductEntity();
            productEntity.setId(generateProductId());
            productEntity.setAudit(auditService.newAudit());
        }

        products.put(productEntity.getId(), productEntity);

        return productEntityMapper.partialUpdate(dto, productEntity);
    }

    private void isValidProduct(ProductDto dto) {

        if (isNullOrEmpty(dto.getName()))
            throw new InvalidDataException("Product name cannot be empty");

        if (dto.getPrice() == null || dto.getPrice() < 0)
            throw new InvalidDataException("Product price cannot be null or negative");

        if (dto.getQuantity() == null || dto.getQuantity() < 0)
            throw new InvalidDataException("Product quantity cannot be null or negative");

    }

    private static long generateProductId() {
        return idCounter.getAndIncrement();
    }

    private Boolean isNullOrEmpty(String item) {
        return item == null || item.equals("");
    }

}