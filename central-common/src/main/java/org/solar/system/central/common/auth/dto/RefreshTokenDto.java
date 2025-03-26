package org.solar.system.central.common.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonInclude(value = Include.NON_NULL)
public class RefreshTokenDto implements Serializable {

	private Long id;

	private Long userId;

	private String token;

	private LocalDateTime expiryDate;

}
