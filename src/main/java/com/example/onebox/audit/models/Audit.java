package com.example.onebox.audit.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Embeddable
@Getter
@Setter
public class Audit {

    private Timestamp audCreationDate;

    private Timestamp audModificationDate;

    private Integer audVersion;

    private Boolean audDeleted;

}
