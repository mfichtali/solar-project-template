package org.solar.system.central.common.all.config;

import java.io.Serializable;
import java.util.Map;

import lombok.*;
import lombok.experimental.Accessors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Accessors(chain = true)
public class AppHealthyChecker implements Serializable{
    
    private static final long serialVersionUID = 1L;
	private String msStatus;
	private String kafkaStatus;
	private Map<String, String> topicsInformation;
}
