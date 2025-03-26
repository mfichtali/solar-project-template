package org.solar.system.central.common.auth.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonInclude(value = Include.NON_NULL)
public class UserDto implements Serializable{
	
	private static final long serialVersionUID = -8541642606436814452L;

	private Long id;
	
	private String firstName;
	
	private String lastName;

	private String email;

	private String username;
	
	private LocalDateTime dateActivation;
	
	private LocalDateTime dateBlocking;
	
	private LocalDateTime dateUnblocking;

	private boolean blocked;
	
	private boolean activation;
	
	private boolean tokenExpired;

//	private boolean tokenRevoked;
	
	private LocalDateTime lastDbConnect;
	
	private String userHashAccount;
	
    private Set<RoleDto> roles = new HashSet<>();

}
