package org.solar.system.mdm.model.repository.vehicle;

import org.solar.system.mdm.model.bo.vehicle.VehicleBasicInfoBo;
import org.solar.system.mdm.model.entities.vehicle.VehicleRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface VehicleRefRepository extends JpaRepository<VehicleRef, UUID> {

    boolean existsByWwRegistrationNumber(String wwRegistrationNumber);
    boolean existsByRegistrationNumber(String registrationNumber);
    boolean existsBySerialChassisNumber(String serialChassisNumber);

    @Query("SELECT v " +
            " FROM VehicleRef as v " +
            " LEFT JOIN v.calendarBillings cdb " +
            " LEFT JOIN v.cautionBillings ctb " +
            " WHERE v.registrationNumber = :regNumberParam " +
            " or v.wwRegistrationNumber = :regNumberParam ")
    Optional<VehicleRef> getByRegistrationNumber(@Param("regNumberParam") String regNumberParam);

    @Query("SELECT COALESCE(v.registrationNumber, v.wwRegistrationNumber)  " +
            " FROM VehicleRef as v " +
            " WHERE v.registrationNumber = :regNumberParam " +
            " or v.wwRegistrationNumber = :regNumberParam ")
    String getUsedRegistrationNumber(@Param("regNumberParam") String regNumberParam);

    @Query("SELECT new org.solar.system.mdm.model.bo.vehicle.VehicleBasicInfoBo(v.id, COALESCE(v.registrationNumber, v.wwRegistrationNumber)) " +
            " FROM VehicleRef as v " +
            " WHERE v.registrationNumber = :regNumberParam " +
            " or v.wwRegistrationNumber = :regNumberParam ")
    Optional<VehicleBasicInfoBo> getVehicleBasicInfoByRegistrationNumber(@Param("regNumberParam") String regNumberParam);

    @Query("SELECT new org.solar.system.mdm.model.bo.vehicle.VehicleBasicInfoBo(v.id, COALESCE(v.registrationNumber, v.wwRegistrationNumber)) " +
            " FROM VehicleRef as v WHERE v.id = :vehicleId ")
    Optional<VehicleBasicInfoBo> getVehicleBasicInfoByIdentifier(@Param("vehicleId") UUID vehicleId);

    @Query("SELECT v " +
            " FROM VehicleRef as v " +
            " WHERE v.registrationNumber = :regNumberParam " +
            " or v.wwRegistrationNumber = :regNumberParam ")
    VehicleRef findByRegistrationNumber(@Param("regNumberParam") String regNumberParam);
    Optional<VehicleRef> findByWwRegistrationNumber(String wwRegistrationNumber);


}
