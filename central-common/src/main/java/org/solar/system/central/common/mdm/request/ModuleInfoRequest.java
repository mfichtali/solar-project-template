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
public class ModuleInfoRequest implements Serializable{

    @NotEmpty(message = "{mdm.module.register.code.notEmpty}")
    private String code;

    @NotEmpty(message = "{mdm.module.register.apiCode.notEmpty}")
    private String apiCode;

	private String label;
}
