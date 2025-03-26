package org.solar.system.central.common.all.messaging;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.function.Supplier;

@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
public class MonoDataKF<T> extends KafkaDomain implements Serializable {

    private T data;

    public <G> MonoDataKF<G> transformAndMap(Supplier<G> transform) {

        return MonoDataKF.<G>builder()
                .action(this.getAction()).msCode(this.getMsCode()).createAt(this.getCreateAt())
                .messageKeyEnum(this.getMessageKeyEnum())
                .messageKey(this.getMessageKey()).partition(this.getPartition()).offset(this.getOffset())
                .groupsToDelivery(this.getGroupsToDelivery()).deliveryGroup(this.getDeliveryGroup())
                .className(this.getClassName()).methodName(this.getMethodName()).classOfCast(this.getClassOfCast())
                .data(transform.get())
                .traceId(this.getTraceId()).spanId(this.getSpanId())
                .errorMessage(this.getErrorMessage())
                .build();
    }

}