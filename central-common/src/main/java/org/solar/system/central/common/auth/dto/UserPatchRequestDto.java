package org.solar.system.central.common.auth.dto;

import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Date;

public class UserPatchRequestDto implements Serializable {

	@NotEmpty(message = "{auth.user.register.firstname.notEmpty}")
	private String firstname;

	@NotEmpty(message = "{auth.user.register.lastname.notEmpty}")
	private String lastname;
	
	@NotEmpty(message = "{auth.user.register.birthdate.notEmpty}")
	private Date birthdate;
}
