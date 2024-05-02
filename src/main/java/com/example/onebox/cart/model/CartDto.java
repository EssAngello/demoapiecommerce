package com.example.onebox.cart.model;

import com.example.onebox.audit.models.AuditDto;
import com.example.onebox.cartrow.model.CartRowDto;
import com.example.onebox.customer.model.CustomerDto;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class CartDto {

    private Long id;

    private CustomerDto customer;

    private Timestamp purchaseDate;

    private List<CartRowDto> cartRows;

    @Embedded
    private AuditDto audit;

}
