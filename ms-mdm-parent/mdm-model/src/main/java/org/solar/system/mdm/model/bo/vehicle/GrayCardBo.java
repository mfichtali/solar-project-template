package org.solar.system.mdm.model.bo.vehicle;

import jakarta.validation.constraints.NotNull;
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
public class GrayCardBo implements Serializable {

    @NotNull
    private String owner;

    @NotNull
    private String address;
    private String usage;

    @NotNull
    private LocalDate startDateCirculationMaroc;

    @NotNull
    private LocalDate dateCardEndValidity;

    @NotNull
    private LocalDate dateOperation;

    @NotNull
    private String serialOperationNumber;

}
