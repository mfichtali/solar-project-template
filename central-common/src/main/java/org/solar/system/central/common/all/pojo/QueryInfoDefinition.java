package org.solar.system.central.common.all.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.solar.system.central.common.all.enums.PartitionQueryTypeEnum;

import java.io.Serializable;

@Builder
@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryInfoDefinition implements Serializable {

    private String entityName;
    private String entityIdentifier;
    private PartitionQueryTypeEnum queryType;
    private String query;
    private String propertyNames;
    private String propertyValues;
    private String propertyChangeValues;

}
