package com.example.onebox.audit.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Embeddable
@Getter
@Setter
@ToString
public class AuditDto {

    private Timestamp audCreationDate;

    private Timestamp audModificationDate;

    private Integer audVersion;

    private Boolean audDeleted;

}
