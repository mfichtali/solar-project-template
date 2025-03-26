package org.solar.system.central.common.vehicle.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.solar.system.central.common.auth.enums.AuthLanguageSupported;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum CoverageTypeEnum {

    COMPREHENSIVE ("COMP", "Tous risques"),
    COLLISION ("COLL", "Collision"),
    LIABILITY ("LIAB", "Responsabilité civile"),
    THEFT ("THEFT", "Vol"),
    FIRE ("FIRE", "Incendie"),
    NATURAL_DISASTERS ("NAT_DIS", "Catastrophes naturelles"),
    GLASS_BREAKAGE ("GLASS", "Brise vert")
    ;

    private final String code;
    private final String description;

    public static CoverageTypeEnum asEnum(String coverType) {
        return Arrays.stream(CoverageTypeEnum.values())
                .filter(c -> c.name().equals(coverType))
                .findFirst().orElse(null);
    }

}
