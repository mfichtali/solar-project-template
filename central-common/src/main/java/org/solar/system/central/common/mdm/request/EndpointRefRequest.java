package org.solar.system.central.common.mdm.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode
@ToString(doNotUseGetters = true)
public class EndpointRefRequest implements Serializable{
	
	@NotEmpty(message = "{mdm.module.register.endpoint.url.notEmpty}")
	private String url;
	
	private Boolean active;

}
