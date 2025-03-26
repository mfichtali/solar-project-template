package org.solar.system.central.common.mdm.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode
@ToString(doNotUseGetters = true)
@JsonInclude(value = Include.NON_NULL)
public class ModuleRefDto implements Serializable{
	
	private UUID id;
	private String code;
	private String apiCode;
	private String label;
	private List<EndpointRefDto> endpointRefs;
	private List<VersionRefDto> versionRefs;
	
}
