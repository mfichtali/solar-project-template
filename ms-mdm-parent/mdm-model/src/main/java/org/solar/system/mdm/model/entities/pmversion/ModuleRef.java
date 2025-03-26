package org.solar.system.mdm.model.entities.pmversion;

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
import java.util.HashSet;
import java.util.Set;

import static org.solar.system.central.common.all.utils.ConstantUtils.CACHE_PREFIX;

@Entity
@Table(name = TableNamesUtils.MODULE_REF, schema = TableNamesUtils.MDM_DEFAULT_SCHEMA)
@SQLRestriction("is_deleted = false")
@DynamicUpdate
@DynamicInsert
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@HistorizeExcludeProperties(exclude = {"versionRefs", "endpointRefs"})
public class ModuleRef extends AbstractAuditingUuidEntity {
	
    @ToString.Include
    @EqualsAndHashCode.Include
    private String code;

    @ToString.Include
    @EqualsAndHashCode.Include
    private String apiCode;

    @ToString.Include
    @EqualsAndHashCode.Include
    private String label;

    @OneToMany(mappedBy = "moduleRef", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<EndpointRef> endpointRefs = new HashSet<>(0);

    @OneToMany(mappedBy = "moduleRef", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<VersionRef> versionRefs = new HashSet<>(0);

    public void addEndpointRef(EndpointRef edp) {

        if (edp == null) {
            throw new IllegalArgumentException("EndpointRef cannot be null");
        }

        if (this.endpointRefs.contains(edp)) {
            // Handle duplicate coverage (e.g., throw an exception, update existing coverage)
            throw new IllegalArgumentException("Endpoint already exists for this configuration.");
        }

        edp.setDateActivation(edp.isActive() ? ZonedDateTime.now() : null);
        this.endpointRefs.add(edp);
        edp.setModuleRef(this);

    }

    public void addVersionRef(VersionRef vrs) {

        if (vrs == null) {
            throw new IllegalArgumentException("VersionRef cannot be null");
        }


        if (this.versionRefs.contains(vrs)) {
            // Handle duplicate coverage (e.g., throw an exception, update existing coverage)
            throw new IllegalArgumentException("VersionRef already exists for this configuration.");
        }

        vrs.setDateActivation(vrs.isActive() ? ZonedDateTime.now() : null);
        this.versionRefs.add(vrs);
        vrs.setModuleRef(this);

    }

    public static String cacheEntityName() {
        return CACHE_PREFIX.concat(ModuleRef.class.getSimpleName());
    }

}
