package org.solar.system.central.common.auth.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RoleDto implements Serializable{
	
	private static final long serialVersionUID = 656407907105694843L;

	private Long id;

    private String name;
    
    private Set<PrivilegeDto> privileges = new HashSet<>();
    
}
