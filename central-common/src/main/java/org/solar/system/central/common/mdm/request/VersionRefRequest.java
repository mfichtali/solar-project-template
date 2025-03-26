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
public class VersionRefRequest implements Serializable {

	@NotEmpty(message = "{mdm.module.register.version.number.notEmpty}")
	private String versionNumber;
	
	private Boolean active;
	
}
