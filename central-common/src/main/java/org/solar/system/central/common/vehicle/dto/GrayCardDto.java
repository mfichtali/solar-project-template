package org.solar.system.central.common.vehicle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class GrayCardDto implements Serializable {

    private String owner;
    private String address;
    private String usage;

    private LocalDate startDateCirculationMaroc;

    private LocalDate dateCardEndValidity;

    private LocalDate dateOperation;

    private String serialOperationNumber;
}
