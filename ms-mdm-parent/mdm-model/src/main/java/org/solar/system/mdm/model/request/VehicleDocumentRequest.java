package org.solar.system.mdm.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.solar.system.central.common.all.enums.DocumentTypeEnum;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class VehicleDocumentRequest implements Serializable {
    protected DocumentTypeEnum documentType;
    protected UUID objectIdentifier;
    protected String objectReference;
    protected UUID blobStorageId;
}
