package org.solar.system.mdm.model.entities.vehicle;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.solar.system.central.common.all.enums.ActionSystemEnum;
import org.solar.system.central.common.all.enums.KafkaKeyLabelEnum;
import org.solar.system.central.common.all.utils.TableNamesUtils;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = TableNamesUtils.T_SCHEDULE_TASK_TX_OUTBOX, schema = TableNamesUtils.MDM_DEFAULT_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class ScheduleTaskTxOutbox implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID id;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "co_action_tx", nullable = false)
    private ActionSystemEnum actionTx;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "entity_id", nullable = false)
    private UUID entityIdentifier;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private String entityName;

    @CreatedDate
    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDateTime creationDate;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "payload_content", nullable = false)
    private String payload;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "co_kafka_key", nullable = false)
    private KafkaKeyLabelEnum codeKafkaKey;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "publish_group_delivery", nullable = false)
    private String groupsToDelivery;

    @Column(name = "in_processed", nullable = false)
    private Boolean processed;

    @Column(name = "err_message")
    private String errorMessage;

    private String classExceptionName;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private String classInvokeName;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private String methodInvokeName;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private String traceId;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private String spanId;

    @PrePersist
    public void prePersist() {
        this.creationDate = LocalDateTime.now();
    }

    public Set<String> getDeliveryGroups() {
        if (StringUtils.isBlank(this.groupsToDelivery)) {
            return new HashSet<>();
        }
        String[] groupArrays = this.groupsToDelivery.split(",");
        return new HashSet<>(Arrays.asList(groupArrays));

    }

}
