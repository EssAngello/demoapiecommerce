package com.example.onebox.cart;

import com.example.onebox.cart.model.CartDto;
import com.example.onebox.cart.model.CartEntity;

import java.util.List;

/**
 * CartService interface provides the structure for the service layer of the Cart.
 * It defines the methods that are used to manipulate and retrieve Cart data.
 */
public interface CartService {

    /**
     * Retrieves a CartEntity by its id.
     *
     * @param id the id of the cart to retrieve
     * @return the CartEntity with the given id
     */
    CartEntity getCart(Long id);

    /**
     * Retrieves all CartEntities.
     *
     * @return a list of all CartEntities
     */
    List<CartEntity> getAllCarts();

    /**
     * Retrieves all CartEntities associated with a specific customer.
     *
     * @param id the id of the customer
     * @return a list of CartEntities associated with the customer
     */
    List<CartEntity> getCartsByCustomerId(Long id);

    /**
     * Retrieves the last active CartEntity associated with a specific customer.
     *
     * @param id the id of the customer
     * @return the last active CartEntity associated with the customer
     */
    CartEntity getLastActiveCartByCustomerId(Long id);

    /**
     * Deletes a CartEntity by its id.
     *
     * @param id the id of the cart to delete
     */
    void delete(Long id);

    /**
     * Saves a CartEntity to the database.
     *
     * @param data the CartDto containing the data to save
     * @return the saved CartEntity
     */
    CartEntity save(CartDto data);

}
