package com.example.onebox.cartrow.model;

import com.example.onebox.audit.models.Audit;
import com.example.onebox.product.model.ProductDto;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRowDto {

    private Long id;

    private Long cartId;

    private ProductDto product;

    private Integer quantity;

    @Embedded
    private Audit audit;

}
