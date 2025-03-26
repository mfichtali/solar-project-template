package org.solar.system.mdm.model.repository.vehicle;

import org.solar.system.central.common.all.enums.DocumentTypeEnum;
import org.solar.system.mdm.model.entities.vehicle.BlobStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BlobStorageRepository extends JpaRepository<BlobStorage, UUID> {
    Optional<BlobStorage> findByObjectReferenceAndDocumentType(String objectRef, DocumentTypeEnum docTypeEnum);
}
