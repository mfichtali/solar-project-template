package org.solar.system.mdm.model.entities.vehicle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.*;
import org.solar.system.central.common.all.utils.DateUtils;
import org.solar.system.central.common.all.utils.ServiceUtils;
import org.solar.system.central.common.all.utils.TableNamesUtils;
import org.solar.system.central.common.vehicle.enums.EnergyTypeEnum;
import org.solar.system.central.common.vehicle.enums.GearboxTypeEnum;
import org.solar.system.central.common.vehicle.enums.VehicleTypeEnum;
import org.solar.system.mdm.model.base.AbstractAuditingUuidEntity;
import org.solar.system.mdm.model.entities.pmbilling.CalendarBillingRef;
import org.solar.system.mdm.model.entities.pmbilling.CautionBillingRef;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.solar.system.central.common.all.utils.ConstantUtils.CACHE_PREFIX;
import static org.solar.system.mdm.model.entities.vehicle.VehicleRef.CURRENT_CALENDAR_BILLING;
import static org.solar.system.mdm.model.entities.vehicle.VehicleRef.DEF_FILTER_CURRENT_YEAR;

@Entity
@Table(name = TableNamesUtils.VEHICLE_REF, schema = TableNamesUtils.MDM_DEFAULT_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, doNotUseGetters = true, onlyExplicitlyIncluded = true)
@DynamicUpdate
@DynamicInsert
@FilterDef(name = CURRENT_CALENDAR_BILLING, parameters = @ParamDef(name = DEF_FILTER_CURRENT_YEAR, type = String.class))
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleRef extends AbstractAuditingUuidEntity {

    public static final String CURRENT_CALENDAR_BILLING = "filterCalendarBillingOfCurrentYear";
    public static final String DEF_FILTER_CURRENT_YEAR = "currentYear";

    @ToString.Include
    @EqualsAndHashCode.Include
    private String marque;

    @ToString.Include
    private String model;

    @ToString.Include
    private String finishing;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "registration_number", unique = true)
    private String registrationNumber;

    @ToString.Include
    @Column(name = "ld_change_registration_number")
    private LocalDate dateChangeWwRegistration;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "ww_registration_number", nullable = false, unique = true)
    private String wwRegistrationNumber;

    @Setter(AccessLevel.NONE)
    @Transient
    private String registrationReference;

    @ToString.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "co_energy_type", nullable = false)
    private EnergyTypeEnum energyType;

    @ToString.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "co_gearbox_type", nullable = false)
    private GearboxTypeEnum gearboxType;

    @Enumerated(EnumType.STRING)
    @Column(name = "co_vehicle_type", nullable = false)
    private VehicleTypeEnum vehicleType;

    @ToString.Include
    @Column(name = "ld_start_circulation", nullable = false)
    private LocalDate startMiseCirculation;

    @ToString.Include
    private Long mileage;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "serial_chassis_number", unique = true, nullable = false)
    private String serialChassisNumber;
    private String color;
    private Long numberCylinders;
    private Long numberPlaces;

    @ToString.Include
    private String fiscalPower;

    @ToString.Include
    private String dinPower;

    @Setter(AccessLevel.NONE)
    @Digits(integer = 8, fraction = 2)
    @Column(name = "mt_purchase", precision = 10, scale = 2)
    private BigDecimal amountPurchase;

    @Setter(AccessLevel.NONE)
    @Digits(integer = 8, fraction = 2)
    @Column(name = "mt_purchase_with_tax", precision = 10, scale = 2)
    private BigDecimal amountPurchaseIncludingTax;

    private String contactPurchaseReference;

    /* HANDLE FLAG Active Operational*/
    @ToString.Include
    private boolean inActiveOperational;

    @Column(name = "commentary_content")
    private String commentary;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "vehicle")
    private GrayCardRef grayCard;

    // bi-directional many-to-one association to HistoricalVehicle
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicle", fetch = FetchType.LAZY)
    @OrderBy("startEventDate DESC")
    private Set<HistoricalVehicle> historicalVehicles = new HashSet<>(0);

    // bi-directional many-to-one association to CalendarBillingRef
    @JsonManagedReference
    @Filter(name = CURRENT_CALENDAR_BILLING, condition = "ref_year = :currentYear")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicle", fetch = FetchType.EAGER)
    private Set<CalendarBillingRef> calendarBillings = new HashSet<>(0);

    @Transient
    private CalendarBillingRef currentCalendarBilling;

    // bi-directional many-to-one association to CautionBillingRef
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicle", fetch = FetchType.EAGER)
    private Set<CautionBillingRef> cautionBillings = new HashSet<>(0);

    @Transient
    private CautionBillingRef currentCautionBilling;

    public CautionBillingRef getCurrentCautionBilling() {
        currentCautionBilling = ServiceUtils.safeSet(cautionBillings)
                .stream()
                .filter(c -> c.getEndCautionDate() == null)
                .findFirst().orElse(null);
        return currentCautionBilling;
    }

    public String getRegistrationReference() {
        if(StringUtils.isNotBlank(registrationNumber)) return registrationNumber;
        return wwRegistrationNumber;
    }

    public HistoricalVehicle getLastHistoricalHVehicle() {
        return this.historicalVehicles.stream().max(Comparator.comparing(HistoricalVehicle::getStartEventDate)).orElse(null);
    }

    public CalendarBillingRef getCurrentCalendarBilling() {

        String currentMonth = DateUtils.getCurrentMonth();
        String currentYear = DateUtils.getCurrentYear();
        currentCalendarBilling = ServiceUtils.safeSet(calendarBillings)
                .stream()
                .filter(c -> Objects.equals(currentMonth, c.getMonth()) && Objects.equals(currentYear, c.getYear()))
                .findFirst().orElse(null);
        return currentCalendarBilling;

    }

    public void setGrayCard(GrayCardRef grayCard) {
        grayCard.setVehicle(this);
        this.grayCard = grayCard;
    }

    public void setAmountPurchase(@Digits(integer = 8, fraction = 2) BigDecimal amountPurchase) {
        this.amountPurchase = amountPurchase;
        this.amountPurchaseIncludingTax = ServiceUtils.addPercent(
                this.amountPurchase,
                BigDecimal.valueOf(20));
    }

    public VehicleRef attachHistoricalVehicles(HistoricalVehicle hs) {

        if (hs == null) {
            throw new IllegalArgumentException("HistoricalVehicle cannot be null");
        }

        historicalVehicles.add(hs);
        hs.setVehicle(this);
        return this;
    }

    public VehicleRef attachCautionBilling(CautionBillingRef ccf) {

        if (ccf == null) {
            throw new IllegalArgumentException("CautionBillingRef cannot be null");
        }

        this.cautionBillings.forEach(c -> {
                    if (Objects.isNull(c.getEndCautionDate())) {
                        c.setEndCautionDate(ccf.getStartCautionDate().minusDays(1));
                    }
                });
        this.cautionBillings.add(ccf);
        ccf.setVehicle(this);
        return this;

    }

    public VehicleRef attachCalendarBilling(CalendarBillingRef calendar) {

        if (calendar == null) {
            throw new IllegalArgumentException("CalendarBillingRef cannot be null");
        }

        calendarBillings.add(calendar);
        calendar.setVehicle(this);
        return this;
    }

    public VehicleRef mergeCalendarBillings(Collection<CalendarBillingRef> calendars) {

        if(CollectionUtils.isEmpty(calendars)) {
            return this;
        }

        for (CalendarBillingRef c : calendars) {
            if (!this.calendarBillings.contains(c)) {
                this.calendarBillings.add(c);
                c.setVehicle(this);
            } else {
                // Handle duplicate coverage (e.g., throw an exception, update existing coverage)
                throw new IllegalArgumentException("CalendarBillingRef already exists for this vehicle.");
            }
        }

        return this;

    }

    public static String cacheEntityName() {
        return CACHE_PREFIX.concat(VehicleRef.class.getSimpleName());
    }
}