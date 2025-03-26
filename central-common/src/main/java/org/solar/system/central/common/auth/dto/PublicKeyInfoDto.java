package org.solar.system.central.common.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicKeyInfoDto implements Serializable{

	private String algorithm;
	
	@JsonProperty("publicKey")
	private String pubKey;

	public static PublicKeyInfoDto mock() {
		return PublicKeyInfoDto.builder().build();
	}
	
}
