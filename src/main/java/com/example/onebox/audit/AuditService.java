package com.example.onebox.audit;

import com.example.onebox.audit.models.Audit;

public interface AuditService {

    Audit newAudit();

    Audit editAudit(Audit audit);

    Audit deleteAudit(Audit audit);

}
