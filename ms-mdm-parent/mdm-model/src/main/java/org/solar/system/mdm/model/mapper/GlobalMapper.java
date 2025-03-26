package org.solar.system.mdm.model.mapper;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.solar.system.central.common.all.enums.ActionSystemEnum;
import org.solar.system.central.common.all.pojo.QueryInfoDefinition;
import org.solar.system.central.common.all.pojo.RecordUpdateField;
import org.solar.system.central.common.all.pojo.RecordWrapper;
import org.solar.system.central.common.all.utils.DateUtils;
import org.solar.system.central.common.all.utils.ServiceUtils;
import org.solar.system.central.common.audit.dto.AuditErrorInfo;
import org.solar.system.central.common.mdm.dto.ModuleInfoDto;
import org.solar.system.central.common.mdm.dto.ModuleRefDto;
import org.solar.system.central.common.mdm.request.EndpointRefRequest;
import org.solar.system.central.common.mdm.request.ModuleInfoRequest;
import org.solar.system.central.common.mdm.request.VersionRefRequest;
import org.solar.system.central.common.vehicle.dto.CalendarBillingDto;
import org.solar.system.central.common.vehicle.dto.CautionBillingDto;
import org.solar.system.central.common.vehicle.dto.GrayCardDto;
import org.solar.system.central.common.vehicle.dto.HistoricalVehicleDto;
import org.solar.system.central.common.vehicle.dto.VehicleBasicInfoDto;
import org.solar.system.central.common.vehicle.dto.VehicleDto;
import org.solar.system.central.common.vehicle.messaging.CreateInspectionVehicleMessage;
import org.solar.system.central.common.vehicle.messaging.CreateTSAVVehicleMessage;
import org.solar.system.central.common.vehicle.request.CalendarBillingRequest;
import org.solar.system.central.common.vehicle.request.CautionBillingRequest;
import org.solar.system.mdm.model.bo.vehicle.GrayCardBo;
import org.solar.system.mdm.model.bo.vehicle.VehicleBasicInfoBo;
import org.solar.system.mdm.model.bo.vehicle.VehicleBo;
import org.solar.system.mdm.model.entities.pmbilling.CalendarBillingRef;
import org.solar.system.mdm.model.entities.pmbilling.CautionBillingRef;
import org.solar.system.mdm.model.entities.pmversion.EndpointRef;
import org.solar.system.mdm.model.entities.pmversion.ModuleRef;
import org.solar.system.mdm.model.entities.pmversion.VersionRef;
import org.solar.system.mdm.model.entities.vehicle.GrayCardRef;
import org.solar.system.mdm.model.entities.vehicle.HistoricalVehicle;
import org.solar.system.mdm.model.entities.vehicle.ScheduleTaskTxOutbox;
import org.solar.system.mdm.model.entities.vehicle.VehicleRef;
import org.solar.system.mdm.model.request.VehicleInspectionDocumentRequest;
import org.solar.system.mdm.model.request.VehicleTSAVDocumentRequest;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GlobalMapper {
	
	GlobalMapper INSTANCE = Mappers.getMapper(GlobalMapper.class);

	EndpointRef endpointRequestToEntity(EndpointRefRequest dto);


	@Mapping(target = "dateActivation", ignore = true)
	@Mapping(target = "moduleRef", ignore = true)
	@Mapping(target = "moduleId", ignore = true)
	VersionRef versionRequestToEntity(VersionRefRequest request);


	@Mapping(target = "code", qualifiedByName = "uppercase")
	@Mapping(target = "endpointRefs", ignore = true)
	@Mapping(target = "versionRefs", ignore = true)
	ModuleRef moduleInfoReqToEntity(ModuleInfoRequest request);
	List<ModuleRef> moduleInfoReqToEntities(List<ModuleInfoRequest> request);

	ModuleInfoDto moduleRefToInfoDto(ModuleRef entity);
	List<ModuleInfoDto> moduleRefToInfoDtos(List<ModuleRef> entities);
	
	
	ModuleRefDto moduleRefToDto(ModuleRef entity);
	List<ModuleRefDto> moduleRefToDtos(List<ModuleRef> entity);
	
    @Named("uppercase")
    default String uppercase(String value) {
        return value == null ? null : value.toUpperCase(Locale.ROOT);
    }

	/*
	 * Vehicle Mapper
	 */

	VehicleBasicInfoDto vehicleBoToDto(VehicleBasicInfoBo vehicleBo);

	VehicleRef vehicleBoToEntity(VehicleBo vehicleBo);
	GrayCardRef grayCardBoToEntity(GrayCardBo grayCardBo);

	@Mapping(target = "entityIdentifier", source = "entityIdentifier", qualifiedByName = "toUUIDFormat")
	@Mapping(target = "entityName", source = ".", qualifiedByName = "getRecordQueryInfo")
	@Mapping(target = "processed", expression = "java(false)")
	ScheduleTaskTxOutbox queryInfoToTxOutboxMapper(QueryInfoDefinition queryInfoDefinition, @Context ActionSystemEnum actionSystemEnum, @Context Function<QueryInfoDefinition, String> transform);

	@AfterMapping
	default void postMappingQueryToScheduleTxOutbox(QueryInfoDefinition queryInfoDefinition, @MappingTarget ScheduleTaskTxOutbox scheduleTxOutbox, @Context ActionSystemEnum actionSystemEnum, @Context Function<QueryInfoDefinition, String> transform) {
		String payloadContent = transform.apply(queryInfoDefinition);
		scheduleTxOutbox.setPayload(Optional.ofNullable(payloadContent).orElse("UNKOWN"));
		scheduleTxOutbox.setActionTx(actionSystemEnum);
	}

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "creationDate", ignore = true)
	@Mapping(target = "entityIdentifier", source = "id")
	@Mapping(target = "entityName", source = ".", qualifiedByName = "getEntityName")
	@Mapping(target = "processed", expression = "java(false)")
	ScheduleTaskTxOutbox vehicleRefToTxOutboxMapper(VehicleRef vehicleRef, @Context ActionSystemEnum actionSystemEnum, @Context Function<VehicleRef, String> transform);

	@AfterMapping
	default void postMappingVehicleRefToScheduleTxOutbox(VehicleRef vehicleRef, @MappingTarget ScheduleTaskTxOutbox scheduleTxOutbox,
														 @Context ActionSystemEnum actionSystemEnum, @Context Function<VehicleRef, String> transform) {
		String payloadContent = transform.apply(vehicleRef);
		scheduleTxOutbox.setPayload(Optional.ofNullable(payloadContent).orElse("UNKOWN"));
		scheduleTxOutbox.setActionTx(actionSystemEnum);
	}

	@Mapping(target = "entityIdentifier", source = "entityId")
	@Mapping(target = "entityName", source = ".", qualifiedByName = "getRecordEntityName")
	@Mapping(target = "processed", expression = "java(false)")
	ScheduleTaskTxOutbox recordUpdateFieldVehicleToTxOutboxMapper(RecordUpdateField<UUID> recordUpdateVehicle, @Context ActionSystemEnum actionSystemEnum, @Context Function<RecordUpdateField<UUID>, String> transform);

	@AfterMapping
	default void postMappingRecordUpdateFieldToScheduleTxOutbox(RecordUpdateField<UUID> recordVehicle, @MappingTarget ScheduleTaskTxOutbox scheduleTxOutbox, @Context ActionSystemEnum actionSystemEnum, @Context Function<RecordUpdateField<UUID>, String> transform) {
		String payloadContent = transform.apply(recordVehicle);
		scheduleTxOutbox.setPayload(Optional.ofNullable(payloadContent).orElse("UNKOWN"));
		scheduleTxOutbox.setActionTx(actionSystemEnum);
	}

	@Mapping(target = "entityIdentifier", source = "entityIdentifier", qualifiedByName = "toUUIDFormat")
	@Mapping(target = "entityName", source = "entityName")
	@Mapping(target = "processed", expression = "java(false)")
	ScheduleTaskTxOutbox recordWrapperToTxOutboxMapper(RecordWrapper record, @Context ActionSystemEnum actionSystemEnum, @Context Function<RecordWrapper, String> transform);

	@AfterMapping
	default void postMappingRecordWrapperToScheduleTxOutbox(RecordWrapper record, @MappingTarget ScheduleTaskTxOutbox scheduleTxOutbox, @Context ActionSystemEnum actionSystemEnum, @Context Function<RecordWrapper, String> transform) {
		String payloadContent = transform.apply(record);
		scheduleTxOutbox.setPayload(Optional.ofNullable(payloadContent).orElse("UNKOWN"));
		scheduleTxOutbox.setActionTx(actionSystemEnum);
	}

	@Mapping(target = "entityIdentifier", expression = "java(java.util.UUID.randomUUID())")
	@Mapping(target = "entityName", source = ".", qualifiedByName = "getAuditErrorEntityName")
	@Mapping(target = "processed", expression = "java(false)")
	ScheduleTaskTxOutbox auditErrorToTxOutboxMapper(AuditErrorInfo record, @Context ActionSystemEnum actionSystemEnum, @Context Function<AuditErrorInfo, String> transform);

	@AfterMapping
	default void postMappingAuditErrorToScheduleTxOutbox(AuditErrorInfo record, @MappingTarget ScheduleTaskTxOutbox scheduleTxOutbox, @Context ActionSystemEnum actionSystemEnum, @Context Function<AuditErrorInfo, String> transform) {
		String payloadContent = transform.apply(record);
		scheduleTxOutbox.setPayload(Optional.ofNullable(payloadContent).orElse("UNKOWN"));
		scheduleTxOutbox.setActionTx(actionSystemEnum);
	}

	@Mapping(target = "registrationNumber", source = "registrationReference")
	VehicleDto vehicleRefToDto(VehicleRef vehicle);

	GrayCardDto grayCardRefToDto(GrayCardRef grayCard);

	List<CalendarBillingDto> calendarBillingSetToDtoList(Set<CalendarBillingRef> set);

	List<CautionBillingDto> cautionBillingSetToDtoList(Set<CautionBillingRef> set);

	@Mapping(target = "startEventDate", qualifiedByName = "formatLocalDateTimeDateAsString")
	@Mapping(target = "endEventDate", qualifiedByName = "formatLocalDateTimeDateAsString")
	HistoricalVehicleDto historicalVehicleToDto(HistoricalVehicle hist);

	@Mapping(target = "amount", source = "amountCautionNoTax")
	CautionBillingRef cautionBillingRequestToEntity(CautionBillingRequest request);

	@Mapping(target = "vehicle", ignore = true)
	@Mapping(target = "month", source = "dueDate", qualifiedByName = "monthFromDueDate")
	@Mapping(target = "year", source = "dueDate", qualifiedByName = "yearFromDueDate")
	@Mapping(target = "amount", source = "amountRentalNoTax")
	CalendarBillingRef calendarBillingRequestToEntity(CalendarBillingRequest request);
	List<CalendarBillingRef> calendarBillingRequestToEntities(List<CalendarBillingRequest> request);

	@Mapping(target = "version", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "creationDate", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "updateDate", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "dueDate", ignore = true)
	@Mapping(target = "deleted", ignore = true)
	CalendarBillingRef copyCalendarBilling(CalendarBillingRef origin);

	@Mapping(target = "idVehicle", source = "objectIdentifier")
	@Mapping(target = "rnVehicle", source = "objectReference")
	@Mapping(target = "idBlobStorage", source = "blobStorageId")
	CreateInspectionVehicleMessage inspectionRequestToMessage(VehicleInspectionDocumentRequest request);

	@Mapping(target = "idVehicle", source = "objectIdentifier")
	@Mapping(target = "rnVehicle", source = "objectReference")
	@Mapping(target = "idBlobStorage", source = "blobStorageId")
	CreateTSAVVehicleMessage tsavRequestToMessage(VehicleTSAVDocumentRequest request);

	@Mapping(target = "entityIdentifier", expression = "java(java.util.UUID.randomUUID())")
	@Mapping(target = "payload", source = "recordContent")
	@Mapping(target = "processed", expression = "java(false)")
	ScheduleTaskTxOutbox stringFeedToTxOutboxMapper(String recordContent, @Context ActionSystemEnum actionSystemEnum,
			@Context String entityName);

	@AfterMapping
	default void postMappingStringFeedToScheduleTxOutbox(@MappingTarget ScheduleTaskTxOutbox scheduleTxOutbox,
			@Context ActionSystemEnum actionSystemEnum, @Context String entityName) {
		scheduleTxOutbox.setEntityName(entityName);
		scheduleTxOutbox.setActionTx(actionSystemEnum);
	}
	
	@Named("monthFromDueDate")
	default String monthFromDueDate(String dueDate) {
		return StringUtils.substring(dueDate,4);
	}

	@Named("getEntityName")
	default String getEntityName(VehicleRef vehicleRef) {
		return vehicleRef.getClass().getSimpleName();
	}

	@Named("getRecordEntityName")
	default String getRecordEntityName(RecordUpdateField<UUID> recordEntity) {
		return recordEntity.getClass().getSimpleName();
	}

	@Named("getRecordQueryInfo")
	default String getRecordQueryInfo(QueryInfoDefinition recordEntity) {
		return recordEntity.getClass().getSimpleName();
	}

	@Named("getAuditErrorEntityName")
	default String getAuditErrorEntityName(AuditErrorInfo recordEntity) {
		return recordEntity.getClass().getSimpleName();
	}

	@Named("yearFromDueDate")
	default String yearFromDueDate(String dueDate) {
		return StringUtils.substring(dueDate,0, 4);
	}

	@Named("toUUIDFormat")
	default UUID toUUIDFormat(String uuidStr) {
		return UUID.fromString(uuidStr);
	}

	@Named("formatLocalDateTimeDateAsString")
	default String formatLocalDateTimeDateAsString(LocalDateTime date) {
		return date == null ? null : DateUtils.toStringFormat(date);
	}

	@AfterMapping
	default List<CalendarBillingDto> afterMappingToListCalendarBillingDto(@MappingTarget final List<CalendarBillingDto> calendarBillingsDto) {
		return  ServiceUtils.safeList(calendarBillingsDto)
				.stream()
				.sorted(Comparator.comparing(CalendarBillingDto::getDueDate).reversed())
				.collect(Collectors.toList());
	}

	@AfterMapping
	default List<CautionBillingDto> afterMappingToListCautionBillingDto(@MappingTarget final List<CautionBillingDto> cautionBillingsDto) {
		return  ServiceUtils.safeList(cautionBillingsDto)
				.stream()
				.sorted(Comparator.comparing(CautionBillingDto::getStartCautionDate).reversed())
				.collect(Collectors.toList());
	}

}
