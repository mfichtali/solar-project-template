package org.solar.system.central.common.all.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class PropertyInputValidator implements Serializable {
	private String index;
    private String propertyName;
    private String message;
}
