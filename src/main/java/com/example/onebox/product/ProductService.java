package com.example.onebox.product;

import com.example.onebox.product.model.ProductDto;
import com.example.onebox.product.model.ProductEntity;

import java.util.List;

/**
 * ProductService interface provides the structure for the service layer of the Product.
 * It defines the methods that are used to manipulate and retrieve Product data.
 */
public interface ProductService {

    /**
     * Retrieves a ProductEntity by its id.
     *
     * @param id the id of the product to retrieve
     * @return the ProductEntity with the given id
     */
    ProductEntity getProduct(Long id);

    /**
     * Retrieves all ProductEntities.
     *
     * @return a list of all ProductEntities
     */
    List<ProductEntity> getAllProducts();

    /**
     * Deletes a ProductEntity by its id.
     *
     * @param id the id of the product to delete
     */
    void delete(Long id);

    /**
     * Saves a ProductEntity to the database.
     *
     * @param data the ProductDto containing the data to save
     * @return the saved ProductEntity
     */
    ProductEntity save(ProductDto data);

}