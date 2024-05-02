package com.example.onebox.cartrow;

import com.example.onebox.cartrow.model.CartRowDto;
import com.example.onebox.cartrow.model.CartRowEntity;

/**
 * CartRowService interface provides the structure for the service layer of the CartRow.
 * It defines the methods that are used to manipulate and retrieve CartRow data.
 */
public interface CartRowService {

    /**
     * Retrieves a CartRowEntity by its id.
     *
     * @param id the id of the cart row to retrieve
     * @return the CartRowEntity with the given id
     */
    CartRowEntity getCartRow(Long id);

    /**
     * Deletes a CartRowEntity by its id.
     *
     * @param id the id of the cart row to delete
     */
    void delete(Long id);

    /**
     * Saves a CartRowEntity to the database.
     *
     * @param data the CartRowDto containing the data to save
     * @return the saved CartRowEntity
     */
    CartRowEntity save(CartRowDto data);

}