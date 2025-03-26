package org.solar.system.mdm.model.repository.pmbilling;

import org.solar.system.mdm.model.entities.pmbilling.CautionBillingRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CautionBillingRefRepository extends JpaRepository<CautionBillingRef, UUID> {
}
