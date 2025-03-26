package org.solar.system.mdm.model.request;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.solar.system.central.common.mdm.request.EndpointRefRequest;
import org.solar.system.central.common.mdm.request.ModuleInfoRequest;
import org.solar.system.central.common.mdm.request.VersionRefRequest;
import org.solar.system.mdm.model.annotations.EndpointUniqueConstraint;
import org.solar.system.mdm.model.annotations.NotEmptyCollectionConstraint;
import org.solar.system.mdm.model.annotations.VersionUniqueConstraint;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParamVersionRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	@Valid
	private ModuleInfoRequest module;

	@NotEmptyCollectionConstraint
	@EndpointUniqueConstraint
	private List<@Valid EndpointRefRequest> endpoints;
	
	@NotEmptyCollectionConstraint
	@VersionUniqueConstraint
	private List<@Valid VersionRefRequest> versions;
	
	@Builder.Default
	private boolean appendEndpoints = true;

	@Builder.Default
	private boolean enableOnParam = false;
	
}
