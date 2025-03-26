package org.solar.system.mdm.model.repository.pmversion;

import org.solar.system.mdm.model.entities.pmversion.ModuleRef;
import org.solar.system.mdm.model.entities.pmversion.VersionRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VersionRefRepository extends JpaRepository<VersionRef, UUID> {

    Optional<List<VersionRef>> findByModuleRef_Code(final String code);
    Optional<List<VersionRef>> findByModuleRef_Id(final UUID id);

    Optional<List<VersionRef>> findByModuleRef(final ModuleRef module);
    
	@Modifying
	@Query("delete from VersionRef m where m.deleted=true")
	void purgeVersion();

}
