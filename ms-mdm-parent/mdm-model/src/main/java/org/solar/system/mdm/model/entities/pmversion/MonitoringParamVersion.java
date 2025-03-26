package org.solar.system.mdm.model.entities.pmversion;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLRestriction;
import org.solar.system.central.common.all.annotations.HistorizeExcludeProperties;
import org.solar.system.central.common.all.utils.TableNamesUtils;
import org.solar.system.mdm.model.base.AbstractAuditingUuidEntity;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = TableNamesUtils.T_MONIT_PARAM_VERSION, schema = TableNamesUtils.MDM_DEFAULT_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@SQLRestriction("is_deleted = false")
@HistorizeExcludeProperties(exclude = {"versionRef", "endpointRef"})
public class MonitoringParamVersion extends AbstractAuditingUuidEntity {
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_version_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private VersionRef versionRef;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "ref_version_id", nullable = false)
    private UUID versionRefId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_endpoint_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private EndpointRef endpointRef;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "ref_endpoint_id", nullable = false)
    private UUID endpointRefId;

    @ToString.Include
    @Column(name = "is_active")
    private boolean active;

    @ToString.Include
    @Column(name = "is_lock")
    private boolean lock;

    @Column(name = "date_lock")
    private ZonedDateTime dateLock;

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();
        if (versionRef != null) {
            versionRefId = versionRef.getId();
        }
        if (endpointRef != null) {
            endpointRefId = endpointRef.getId();
        }
    }
}
