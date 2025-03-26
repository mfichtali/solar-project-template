package org.solar.system.mdm.model.entities.vehicle;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.solar.system.central.common.all.enums.DocumentTypeEnum;
import org.solar.system.central.common.all.utils.TableNamesUtils;
import org.solar.system.mdm.model.base.AbstractAuditingUuidEntity;

import java.util.UUID;

@Entity
@Table(name = TableNamesUtils.T_BLOB_STORAGE, schema = TableNamesUtils.MDM_DEFAULT_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, doNotUseGetters = true, onlyExplicitlyIncluded = true)
@DynamicUpdate
@DynamicInsert
public class BlobStorage extends AbstractAuditingUuidEntity {

    @ToString.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "co_document_type", nullable = false)
    private DocumentTypeEnum documentType;

    @Getter(AccessLevel.NONE)
    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private String documentName;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private String documentExtension;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "id_object", nullable = false)
    private UUID objectIdentifier;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "ref_object")
    private String objectReference;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "object_name", nullable = false)
    private String objectName;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "object_domain", nullable = false)
    private String objectDomain;

    @Lob
    @EqualsAndHashCode.Include
    //@Type(type = "org.hibernate.type.BinaryType")
    //@JdbcTypeCode(Types.BINARY)
    @Column(nullable = false)
    private byte[] blobContent;

    public String getDocumentName() {
        return String.format("%s.%s", documentName, documentExtension);
    }
}
