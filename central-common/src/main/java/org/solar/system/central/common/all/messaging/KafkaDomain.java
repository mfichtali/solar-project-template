package org.solar.system.central.common.all.messaging;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.solar.system.central.common.all.enums.ActionSystemEnum;
import org.solar.system.central.common.all.enums.KafkaKeyLabelEnum;
import org.solar.system.central.common.all.enums.KafkaPartitionEnum;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class KafkaDomain {

	protected String msCode;
	protected ActionSystemEnum action;

	@Builder.Default
	@Setter(AccessLevel.NONE)
	protected LocalDateTime	createAt = LocalDateTime.now();

	@Builder.Default
	protected Set<String> groupsToDelivery = new HashSet<>();

	@Getter(AccessLevel.NONE)
	protected String 	deliveryGroup;

	@Setter(AccessLevel.NONE)
	protected KafkaKeyLabelEnum messageKeyEnum;
	protected String 	messageKey;
	protected Integer 	partition;
	protected Integer 	offset;

	protected String classOfCast;
	@Getter(AccessLevel.NONE)
	protected String 	classOfMessage;
	protected String 	className;
	protected String 	methodName;
	protected String 	errorMessage;

	protected String 	traceId;
	protected String 	spanId;

	public String getClassOfMessage() {
		return String.format("%s.%s", this.getClass().getPackageName(), this.getClass().getSimpleName());
	}

	public String getDeliveryGroup() {
		return String.join(",", groupsToDelivery);
	}

	public void buildMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public void buildMessageKey(String messageKey, Integer partition) {
		if (partition == null) {
			buildMessageKey(messageKey);
		} else {
			this.messageKey = messageKey;
			this.partition = partition;
		}
	}

	public void buildMessageKey(KafkaKeyLabelEnum messageKeyEnum) {
		this.messageKeyEnum = messageKeyEnum;
		if (messageKeyEnum != null) {
			this.messageKey = messageKeyEnum.getKeyLabel();
			this.partition = Integer.valueOf(KafkaPartitionEnum.enumByKafkaKey(messageKeyEnum).getPartition());
		}
	}
	
}
