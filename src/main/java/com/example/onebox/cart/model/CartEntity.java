package com.example.onebox.cart.model;

import com.example.onebox.audit.models.Audit;
import com.example.onebox.cartrow.model.CartRowEntity;
import com.example.onebox.customer.model.CustomerEntity;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class CartEntity {

    private Long id;

    private CustomerEntity customer;

    private Timestamp purchaseDate;

    private List<CartRowEntity> cartRows;

    @Embedded
    private Audit audit;

}
