package org.solar.system.central.common.auth.dto;

import java.io.Serializable;
import java.util.List;

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
public class UserAuthResponseDto implements Serializable {

	private static final long serialVersionUID = 2220989403083804604L;
	private Long id;
	private String firstname;
	private String lastname;
	private String email;
	private String username;
	private String accessToken;
	private String refreshToken;
//	private Date expirationDate;
	private List<String> authorities;

}
