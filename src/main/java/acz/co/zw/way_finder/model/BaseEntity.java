package acz.co.zw.way_finder.model;

import acz.co.zw.way_finder.utils.Constants;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Data
public class BaseEntity implements Serializable {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.AUDIT_DATE_FORMAT);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;
    protected boolean activeStatus;
    @Version
    private long version = 1L;
    @Column(name = "created_by_user", updatable = false)
    @CreatedBy
    private String createdByUser;
    @Column(name = "creation_time", updatable = false)
    private String creationTime;

    @Column(name = "modified_by_user")
    @LastModifiedBy
    private String modifiedByUser;
    @Column(name = "modification_time")
    private String modificationTime;

    @PrePersist
    public void prePersist() {
        this.creationTime = dtf.format(LocalDateTime.now());
        this.modificationTime = dtf.format(LocalDateTime.now());
        this.activeStatus = true;
//        this.createdByUser = usernameAuditorAware.getCurrentAuditor().orElse("Anonymous User");
//        this.modifiedByUser = usernameAuditorAware.getCurrentAuditor().orElse("Anonymous User");
    }

    @PreUpdate
    public void preUpdate() {
        this.modificationTime = dtf.format(LocalDateTime.now());
        // this.modifiedByUser = usernameAuditorAware.getCurrentAuditor().orElse("Anonymous User");
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final BaseEntity other = (BaseEntity) obj;
        return this.id == other.id;
    }

}

