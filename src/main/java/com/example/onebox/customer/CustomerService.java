package com.example.onebox.customer;

import com.example.onebox.customer.model.CustomerDto;
import com.example.onebox.customer.model.CustomerEntity;

import java.util.List;

/**
 * CustomerService interface provides the structure for the service layer of the Customer.
 * It defines the methods that are used to manipulate and retrieve Customer data.
 */
public interface CustomerService {

    /**
     * Retrieves a CustomerEntity by its id.
     *
     * @param id the id of the customer to retrieve
     * @return the CustomerEntity with the given id
     */
    CustomerEntity getCustomer(Long id);

    /**
     * Retrieves all CustomerEntities.
     *
     * @return a list of all CustomerEntities
     */
    List<CustomerEntity> getAllCustomers();

    /**
     * Deletes a CustomerEntity by its id.
     *
     * @param id the id of the customer to delete
     */
    void delete(Long id);

    /**
     * Saves a CustomerEntity to the database.
     *
     * @param data the CustomerDto containing the data to save
     * @return the saved CustomerEntity
     */
    CustomerEntity save(CustomerDto data);

}