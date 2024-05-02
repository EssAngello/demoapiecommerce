package com.example.onebox.product.model;

import com.example.onebox.audit.models.AuditDto;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDto {

    private Long id;

    private String name;

    private String description;

    private String image;

    private String eanCode;

    private Double price;

    private Integer quantity;

    @Embedded
    private AuditDto audit;

}
