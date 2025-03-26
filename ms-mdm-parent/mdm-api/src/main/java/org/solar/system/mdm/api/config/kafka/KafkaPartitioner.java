package org.solar.system.mdm.api.config.kafka;

import org.apache.commons.lang.ArrayUtils;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.solar.system.central.common.all.enums.PartitionQueryTypeEnum;
import org.solar.system.central.common.all.exceptions.InvalidKafkaKeyException;

import java.util.List;
import java.util.Map;

public class KafkaPartitioner implements Partitioner {

	private final int[] partitionExcluded = PartitionQueryTypeEnum.queryPartitionValues();
	
	@Override
	public int partition(String topic, Object key, byte[] keyBytes, //
			Object value, byte[] valueBytes, Cluster cluster) {
		
		List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
		int numPartitions = partitions.size();
		
		if(keyBytes == null || !(key instanceof String)) {
			throw new InvalidKafkaKeyException("All messages should have a valid key");
		}

		int partitionCalculated = Math.abs(key.hashCode()) % numPartitions;
		if (ArrayUtils.contains(partitionExcluded, partitionCalculated)) {

			if(partitionCalculated == 11){
				partitionCalculated = 1;
			} else {
				partitionCalculated++;
			}

		}

		return partitionCalculated;
		
	}
	
	@Override
	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

}
