package org.solar.system.central.common.auth.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class TokenRefreshResponseDto implements Serializable {

	private static final long serialVersionUID = 100289375339891694L;
	private String accessToken;
	private String refreshToken;
}
