package org.solar.system.mdm.model.entities.pmversion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLRestriction;
import org.solar.system.central.common.all.annotations.HistorizeExcludeProperties;
import org.solar.system.central.common.all.utils.TableNamesUtils;
import org.solar.system.mdm.model.base.AbstractAuditingUuidEntity;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.solar.system.central.common.all.utils.ConstantUtils.CACHE_PREFIX;

@Entity
@Table(name = TableNamesUtils.VERSION_REF, schema = TableNamesUtils.MDM_DEFAULT_SCHEMA)
@SQLRestriction("is_deleted = false")
@DynamicUpdate
@DynamicInsert
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@HistorizeExcludeProperties(exclude = {"moduleRef"})
public class VersionRef extends AbstractAuditingUuidEntity {
	
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "version_number")
    private String versionNumber;

    @Column(name = "is_active")
    private boolean active = true;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "date_activation")
    private ZonedDateTime dateActivation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    @JsonIgnore
    private ModuleRef moduleRef;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "module_id", nullable = false)
    private UUID moduleId;

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();
        if (moduleRef != null) {
            moduleId = moduleRef.getId();
        }
    }

    public static String cacheEntityName() {
        return CACHE_PREFIX.concat(VersionRef.class.getSimpleName());
    }

}
