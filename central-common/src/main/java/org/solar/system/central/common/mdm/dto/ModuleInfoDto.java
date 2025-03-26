package org.solar.system.central.common.mdm.dto;

import java.io.Serializable;
import java.util.UUID;

import org.solar.system.central.common.mdm.Views;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(doNotUseGetters = true)
@Builder
@JsonInclude(value = Include.NON_NULL)
public class ModuleInfoDto implements Serializable{
    
	private UUID id;
	@JsonView(Views.NoIdentifierView.class)
	private String code;
	@JsonView(Views.NoIdentifierView.class)
	private String apiCode;
	@JsonView(Views.NoIdentifierView.class)
	private String label;

}
