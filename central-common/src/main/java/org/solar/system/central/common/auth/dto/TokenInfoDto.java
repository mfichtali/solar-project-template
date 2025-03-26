package org.solar.system.central.common.auth.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@Setter	
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TokenInfoDto implements Serializable {

    private boolean validToken;
    private String token;
    private String username;
    private List<String> authorities;
    
}
