package org.solar.system.mdm.api.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.solar.system.central.common.all.exceptions.BusinessException;
import org.solar.system.central.common.all.utils.ConstantUtils;
import org.solar.system.central.common.vehicle.dto.VehicleBasicInfoDto;
import org.solar.system.central.common.vehicle.dto.VehicleDto;
import org.solar.system.central.common.vehicle.request.CalendarBillingRequest;
import org.solar.system.central.common.vehicle.request.CautionBillingRequest;
import org.solar.system.mdm.model.request.VehicleDocumentRequest;
import org.solar.system.mdm.model.request.VehicleInfoRequest;
import org.solar.system.mdm.model.request.VehicleInspectionDocumentRequest;
import org.solar.system.mdm.model.request.VehicleTSAVDocumentRequest;
import org.solar.system.mdm.service.api.VehicleService;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.solar.system.mdm.api.rest.AbstractResource.MDM_VEHICLE_REQUEST_MAPPING;
import static org.solar.system.mdm.api.rest.AbstractResource.VEHICLE_RESOURCE;

@Validated
@RestController(VEHICLE_RESOURCE)
@RequestMapping(value = MDM_VEHICLE_REQUEST_MAPPING)
@RequiredArgsConstructor
public class ApiVehicleResource extends AbstractResource {

    private final VehicleService vehicleService;

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public VehicleDto getVehicleInfo(final @RequestParam(value = "registration-number") String registrationNumber,
            final @RequestParam(value = "full-calendar", required = false, defaultValue = "false") Boolean fullCalendar,
            final @RequestParam(value = ConstantUtils.RQ_USE_CACHE, required = false, defaultValue = "true") boolean useCache) {
        return vehicleService.getVehicleInfo(registrationNumber, fullCalendar, useCache);
    }

    @GetMapping("/basic-info")
    @ResponseStatus(HttpStatus.OK)
    public VehicleBasicInfoDto getVehicleInfo(final @RequestParam(value = "vehicle-id", required = false) String vehicleTechnicalId,
            final @RequestParam(value = "registration-number", required = false) String registrationNumber) {
        return vehicleService.getVehicleBasicInfo(vehicleTechnicalId, registrationNumber);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createVehicleProcess(
            @Valid @RequestBody VehicleInfoRequest data, BindingResult result) {
        vehicleService.createVehicle(data);
    }

    @PostMapping("/define-calendar/{appliedYear}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void defineCalendarBilling(
            final @PathVariable String appliedYear,
            final @RequestParam(value = "registration-number") String registrationNumber,
            final @RequestBody List<CalendarBillingRequest> calendarBillings,
            final @RequestParam(value = ConstantUtils.RQ_USE_CACHE, required = false, defaultValue = "true") boolean useCache) throws BusinessException {
        vehicleService.defineCalendarBilling(registrationNumber, appliedYear, calendarBillings, useCache);
    }

    @PostMapping("/duplicate-calendar/{sourceYear}/to/{targetYear}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void duplicateCalendarBilling(@PathVariable String sourceYear, @PathVariable String targetYear,
            @RequestParam(value = "registration-number") String registrationNumber) throws BusinessException {
        vehicleService.duplicateCalendarBilling(registrationNumber, sourceYear, targetYear);
    }

    @PostMapping("/define-caution")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void defineCautionBilling(
            @RequestParam(value = "registration-number") String registrationNumber,
            @Valid @RequestBody CautionBillingRequest cautionBilling, BindingResult result,
            final @RequestParam(value = ConstantUtils.RQ_USE_CACHE, required = false, defaultValue = "true") boolean useCache) throws BusinessException {
        vehicleService.defineCautionBilling(registrationNumber, cautionBilling, useCache);
    }

    @PatchMapping("/change-registration-number/{wwRegNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeFinalRegistrationNumber(final @PathVariable String wwRegNumber,
                                              final @RequestParam(value = "registration-number") String finalRegNumber,
                                              final @RequestParam(value = ConstantUtils.RQ_USE_CACHE, required = false, defaultValue = "true") boolean useCache) throws BusinessException {
        vehicleService.applyFinalRegistrationNumber(wwRegNumber, finalRegNumber, useCache);
    }

    @PostMapping(value = "/documents/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String uploadDocument(
            final @RequestPart("document") MultipartFile document,
            final @RequestPart("info") VehicleDocumentRequest documentInfo) {
        return vehicleService.uploadVehicleDocument(document, documentInfo);
    }

    @PostMapping(value = "/inspection/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String uploadVehicleInspectionDocument(
            final @RequestPart("document") MultipartFile document,
            final @RequestPart("inspection-info") VehicleInspectionDocumentRequest documentInfo) {
        return vehicleService.uploadInspectionVehicleDocument(document, documentInfo);
    }

    @PostMapping(value = "/tsav/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String uploadVehicleTSAVDocument(
            final @RequestPart("document") MultipartFile document,
            final @RequestPart("tsav-info") VehicleTSAVDocumentRequest documentInfo) {
        return vehicleService.uploadTSAVVehicleDocument(document, documentInfo);
    }

    @GetMapping(value = "/documents/{registration-number}/download/{document-type}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> downloadDocument(
            final @PathVariable("registration-number") String regNumber,
            final @PathVariable("document-type") String docType) {
        Pair<String, byte[]> contentPair = vehicleService.downloadDocumentVehicle(regNumber, docType);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDisposition(ContentDisposition.attachment().filename(contentPair.getLeft()).build());
        return ResponseEntity.ok()
                .contentLength(contentPair.getRight().length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .headers(httpHeaders)
                .body(contentPair.getRight());
    }

    @GetMapping(value = "/documents/{rn-vehicle}/info")
    @ResponseStatus(HttpStatus.OK)
    public void vehicleDocumentInfo(
            final @PathVariable("rn-vehicle") String rnVehicle,
            final @RequestParam(value = "document-type", required = false) String docType) {
        vehicleService.getVehicleDocumentInfo(rnVehicle, docType);
    }

}
