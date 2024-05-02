package com.example.onebox.audit;

import com.example.onebox.audit.models.Audit;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AuditServiceImpl implements AuditService {

    @Override
    public Audit newAudit() {
        Audit audit = new Audit();
        audit.setAudCreationDate(new Timestamp(System.currentTimeMillis()));
        audit.setAudVersion(1);
        audit.setAudDeleted(false);

        return audit;
    }

    @Override
    public Audit editAudit(Audit audit) {
        audit.setAudModificationDate(new Timestamp(System.currentTimeMillis()));
        audit.setAudVersion(audit.getAudVersion() + 1);

        return audit;
    }

    @Override
    public Audit deleteAudit(Audit audit) {
        audit.setAudModificationDate(new Timestamp(System.currentTimeMillis()));
        audit.setAudVersion(audit.getAudVersion() + 1);
        audit.setAudDeleted(true);

        return audit;
    }
}
