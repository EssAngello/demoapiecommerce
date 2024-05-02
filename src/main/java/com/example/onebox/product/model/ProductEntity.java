package com.example.onebox.product.model;

import com.example.onebox.audit.models.Audit;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductEntity {

    private Long id;

    private String name;

    private String description;

    private String image;

    private String eanCode;

    private Double price;

    private Integer quantity;

    @Embedded
    private Audit audit;

}
