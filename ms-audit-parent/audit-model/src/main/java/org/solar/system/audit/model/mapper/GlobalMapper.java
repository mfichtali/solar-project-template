package org.solar.system.audit.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.solar.system.audit.model.entities.AuditDefinitionError;
import org.solar.system.audit.model.entities.AuditDefinitionQuery;
import org.solar.system.audit.model.entities.AuditDefinitionTrace;
import org.solar.system.audit.model.entities.AuditErrorEvent;
import org.solar.system.central.common.all.messaging.MonoDataKF;
import org.solar.system.central.common.all.pojo.QueryInfoDefinition;
import org.solar.system.central.common.audit.dto.AuditErrorInfo;

import java.util.Locale;

@Mapper(componentModel = "spring")
public interface GlobalMapper {

    GlobalMapper INSTANCE = Mappers.getMapper(GlobalMapper.class);


    @Mapping(target = "groupHeader", source = "deliveryGroup")
    @Mapping(target = "metadata", source = "data")
    @Mapping(target = "moduleCode", source = "msCode")
    @Mapping(target = "dateCreation", source = "createAt")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    AuditDefinitionTrace messageToAuditTraceEntity(MonoDataKF<String> data);

    @Mapping(target = "moduleCode", source = "msCode")
    @Mapping(target = "groupHeader", source = "deliveryGroup")
    @Mapping(target = "dateCreation", source = "createAt")
    @Mapping(target = "entityName", source = "data.entityName")
    @Mapping(target = "entityIdentifier", source = "data.entityIdentifier")
    @Mapping(target = "queryType", source = "data.queryType")
    @Mapping(target = "query", source = "data.query", qualifiedByName = "uppercase")
    @Mapping(target = "propertyNames", source = "data.propertyNames")
    @Mapping(target = "propertyValues", source = "data.propertyValues")
    @Mapping(target = "propertyChangeValues", source = "data.propertyChangeValues")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    AuditDefinitionQuery messageToAuditQueryEntity(MonoDataKF<QueryInfoDefinition> data);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "audit", ignore = true)
    AuditErrorEvent toAuditErrorEventEntity(AuditErrorInfo data);

    @Mapping(target = "moduleCode", source = "msCode")
    @Mapping(target = "dateCreation", source = "createAt")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    AuditDefinitionError messageToAuditErrorEntity(MonoDataKF<AuditErrorInfo> data);

    @Named("uppercase")
    default String uppercase(String value) {
        return value == null ? null : value.toUpperCase(Locale.ROOT);
    }

}
