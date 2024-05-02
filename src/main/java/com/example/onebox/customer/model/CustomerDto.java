package com.example.onebox.customer.model;

import com.example.onebox.audit.models.AuditDto;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {

    private Long id;

    private String firstname;

    private String lastname;

    @Embedded
    private AuditDto audit;

}
