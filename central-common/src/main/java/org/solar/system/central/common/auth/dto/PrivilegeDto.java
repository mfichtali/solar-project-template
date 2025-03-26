package org.solar.system.central.common.auth.dto;

import lombok.*;

import java.io.Serializable;

@Data	
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PrivilegeDto implements Serializable{

	private Long id;
	
	private String name;

}
