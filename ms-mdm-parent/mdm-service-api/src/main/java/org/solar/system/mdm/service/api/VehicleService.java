package org.solar.system.mdm.service.api;

import org.apache.commons.lang3.tuple.Pair;
import org.solar.system.central.common.all.exceptions.BusinessException;
import org.solar.system.central.common.all.messaging.MonoDataKF;
import org.solar.system.central.common.vehicle.dto.VehicleBasicInfoDto;
import org.solar.system.central.common.vehicle.dto.VehicleDto;
import org.solar.system.central.common.vehicle.messaging.CreateHistoricalVehicleMessage;
import org.solar.system.central.common.vehicle.request.CalendarBillingRequest;
import org.solar.system.central.common.vehicle.request.CautionBillingRequest;
import org.solar.system.mdm.model.request.VehicleDocumentRequest;
import org.solar.system.mdm.model.request.VehicleInfoRequest;
import org.solar.system.mdm.model.request.VehicleInspectionDocumentRequest;
import org.solar.system.mdm.model.request.VehicleTSAVDocumentRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VehicleService {

    VehicleDto getVehicleInfo(final String registrationNumber, final Boolean fullCalendar, final boolean useCache);

    VehicleBasicInfoDto getVehicleBasicInfo(final String vehicleTechnicalId, final String registrationNumber);

    void createVehicle(final VehicleInfoRequest request);
    void applyFinalRegistrationNumber(final String wwRegNumber, final String finalRegNumber, final boolean useCache) throws BusinessException;

    /* Define calendar */
    void defineCalendarBilling(final String registrationNumber, final String appliedYear,
                               final List<CalendarBillingRequest> calendarBillingRequests, final boolean useCache) throws BusinessException;

    void duplicateCalendarBilling(final String registrationNumber, final String fromYear, final String toYear);

    /* Define caution */
    void defineCautionBilling(final String registrationNumber, CautionBillingRequest cautionBillingRequest, final boolean userCache);

    void createHistoricalEventVehicle(final MonoDataKF<CreateHistoricalVehicleMessage> message);

    String uploadVehicleDocument(MultipartFile file, VehicleDocumentRequest documentRequest);
    
    String uploadInspectionVehicleDocument(MultipartFile file, VehicleInspectionDocumentRequest inspectionDocumentRequest);

    String uploadTSAVVehicleDocument(MultipartFile file, VehicleTSAVDocumentRequest tsavDocumentRequest);

    Pair<String, byte[]> downloadDocumentVehicle(final String regNumber, final String docType);

    void getVehicleDocumentInfo(final String rnVehicle, final String docType);
}
