package org.solar.system.central.common.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserAuthRequestDto implements Serializable{

	@NotEmpty(message = "{auth.user.login.notEmpty}")
	private String login;
	
    @NotEmpty(message = "{auth.user.password.notEmpty}")
	private String password;
    
}
