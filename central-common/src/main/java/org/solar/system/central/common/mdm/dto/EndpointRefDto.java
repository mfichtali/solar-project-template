package org.solar.system.central.common.mdm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(doNotUseGetters = true)
@Builder
@JsonInclude(value = Include.NON_NULL)
public class EndpointRefDto implements Serializable{
	
	private UUID id;
	private String url;
	private Boolean active;
	private String apiCode;

}
