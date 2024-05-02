package com.example.onebox.customer.model;

import com.example.onebox.audit.models.Audit;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerEntity {

    private Long id;

    private String firstname;

    private String lastname;

    @Embedded
    private Audit audit;

}
