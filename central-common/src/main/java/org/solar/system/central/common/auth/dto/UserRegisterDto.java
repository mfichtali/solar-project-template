package org.solar.system.central.common.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.solar.system.central.common.all.utils.ConstantUtils;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class UserRegisterDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "{auth.user.register.firstname.notEmpty}")
	private String firstname;

	@NotEmpty(message = "{auth.user.register.lastname.notEmpty}")
	private String lastname;

	@NotEmpty(message = "{auth.user.register.email.notEmpty}")
	@Email(regexp = ConstantUtils.EMAIL_PATTERN, message = "{auth.user.register.email.valid}" )
	private String email;

	@NotEmpty(message = "{auth.user.register.username.notEmpty}")
	private String username;

	/** Password policy to implement**/
	@NotEmpty(message = "{auth.user.register.password.notEmpty}")
	private String password;

	@NotEmpty(message = "{auth.user.register.matchingPassword.notEmpty}")
	private String matchingPassword;

}
