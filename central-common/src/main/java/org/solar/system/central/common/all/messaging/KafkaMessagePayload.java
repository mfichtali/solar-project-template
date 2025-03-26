package org.solar.system.central.common.all.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.solar.system.central.common.all.enums.ActionSystemEnum;
import org.solar.system.central.common.all.enums.KafkaKeyLabelEnum;

import java.util.Set;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessagePayload<T> {
	private T dataPayload;
	private ActionSystemEnum actionSystem;
	private KafkaKeyLabelEnum messageKeyEnum;
	private String messageKeyString; 
	private Integer partition;
	private Set<String> groupsToDelivery;
	private String classOfCast;
	private String originClassName;
	private String originInvokeMethod;
	private String traceId;
	private String spanId;		
}
