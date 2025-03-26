package org.solar.system.mdm.model.repository.pmversion;

import org.solar.system.mdm.model.entities.pmversion.EndpointRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface EndpointRefRepository extends JpaRepository<EndpointRef, UUID> {

	@Modifying
	@Query("delete from EndpointRef m where m.deleted=true")
	void purgeEndpoint();
}
