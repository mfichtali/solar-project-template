package org.solar.system.mdm.model.repository.pmbilling;

import org.solar.system.mdm.model.entities.pmbilling.CalendarBillingRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CalendarBillingRefRepository extends JpaRepository<CalendarBillingRef, UUID> {

    @Query("SELECT COUNT(1) > 0 "
            + " FROM CalendarBillingRef cr " //
            + " JOIN cr.vehicle v "
            + " WHERE cr.year = :year " //
            + " AND (v.registrationNumber = :regNumberParam or v.wwRegistrationNumber = :regNumberParam) ")
    boolean existsCalendarByVehicleIdAndYear(@Param("regNumberParam") final String regNumber,
                                             @Param("year") final String year);


    @Query("SELECT cr "
            + " FROM CalendarBillingRef cr " //
            + " JOIN cr.vehicle v "
            + " WHERE cr.year = :year " //
            + " AND (v.registrationNumber = :regNumberParam or v.wwRegistrationNumber = :regNumberParam) ")
    List<CalendarBillingRef> findByVehicleAndYear(@Param("regNumberParam") final String regNumber,
                                                  @Param("year") final String year);

}
