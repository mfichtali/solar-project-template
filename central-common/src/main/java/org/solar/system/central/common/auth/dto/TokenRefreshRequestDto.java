package org.solar.system.central.common.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TokenRefreshRequestDto implements Serializable{

	@NotEmpty(message = "{auth.refresh.token.notEmpty}")
	private String refreshToken;

}
