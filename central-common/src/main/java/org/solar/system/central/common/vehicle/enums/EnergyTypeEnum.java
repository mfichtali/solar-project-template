package org.solar.system.central.common.vehicle.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnergyTypeEnum {

    DIESEL("GO", "Gazoile"),
    ESSENCE("ES", "Essence"),
    ELECTRIC("EL", "Electrique"),
    HYBRID_ES("EE", "Essence électricité (hybride rechargeable)"),
    HYBRID_ES_NRC("EH", "Essence-électricité (hybride non rechargeable)"),
    HYBRID_GO("GL", "Gazole-électricité (hybride rechargeable)"),
    HYBRID_GO_NRC("GH", "Gazole-électricité (hybride non rechargeable)")

    ;

    public final String code;
    public final String description;

}
