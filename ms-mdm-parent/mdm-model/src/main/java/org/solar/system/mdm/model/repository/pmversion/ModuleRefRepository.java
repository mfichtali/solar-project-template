package org.solar.system.mdm.model.repository.pmversion;

import org.solar.system.mdm.model.entities.pmversion.ModuleRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRefRepository extends JpaRepository<ModuleRef, UUID> {

	Optional<ModuleRef> findByCode(String code);

	boolean existsByCode(String code);

	List<ModuleRef> findByCodeContaining(String code);

	@Modifying
	@Query("delete from ModuleRef m where m.deleted=true")
	void purgeModule();

}
