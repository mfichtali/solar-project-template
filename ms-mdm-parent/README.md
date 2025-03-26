# MDM Produce message to some MS


| MS Label | Key delivery     | Key-msg | Partition                   | Description                                              |
|:---------|:-----------------| :------- |:----------------------------|:---------------------------------------------------------|
| `AUDIT`  | `MDM_2_AUDIT`    | `KafkaKeyLabelEnum.KEY_QUERY_*` | `KafkaPartitionEnum.THREE`  | Push mesasge of type (CRUD query), and trace it in audit |
| `AUDIT`  | `MDM_2_AUDIT`    | `KafkaKeyLabelEnum.KEY_RENTAL_MANAGE_VEHICLE` | `KafkaPartitionEnum.ONE`    | Push message of manage vehicle                           |
| `AUDIT`  | `MDM_2_AUDIT`    | `KafkaKeyLabelEnum.KEY_AUDIT_ON_ERROR` | `KafkaPartitionEnum.ELEVEN` | Trace error message in audit                             |
| `AUDIT`  | `MDM_2_AUDIT`    | `KafkaKeyLabelEnum.KEY_HISTORIZE_DATA` | `KafkaPartitionEnum.ZERO`   | Trace Historize data                                     |
| `RENTAL` | `MDM_2_RENTAL`   | `KafkaKeyLabelEnum.KEY_RENTAL_MANAGE_VEHICLE` | `KafkaPartitionEnum.ONE`   | Trace Change registration number                         |
