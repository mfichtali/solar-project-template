package org.solar.system.central.common.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(doNotUseGetters = true)
@Builder
@JsonInclude(value = Include.NON_NULL)
public class ApiVersionDto implements Serializable{

	@JsonIgnore
	private Long id;
	
	private String versionNumber;
	
	private LocalDateTime expiryDate;
	
	private boolean activeVersion;
	
	private boolean lockVersion;
	
}
