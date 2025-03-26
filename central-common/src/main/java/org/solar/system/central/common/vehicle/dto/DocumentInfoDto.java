package org.solar.system.central.common.vehicle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.solar.system.central.common.all.enums.DocumentTypeEnum;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class DocumentInfoDto {
	
	
	private String documentName;
	private DocumentTypeEnum documentType;
	private UUID objectId;
	private String objectRef;
	
}
