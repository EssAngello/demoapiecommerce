package com.example.onebox.cartrow.model;

import com.example.onebox.audit.models.Audit;
import com.example.onebox.product.model.ProductEntity;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRowEntity {

    private Long id;

    private Long cartId;

    private ProductEntity product;

    private Integer quantity;

    @Embedded
    private Audit audit;

}
