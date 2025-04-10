package org.solar.system.hexa.domain.model.insurance;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class InsuranceCompanyDomain {

    private String name;
    private String companyIdentifier;
    private String address;
    private String email;
    private String phoneNumber;

}
