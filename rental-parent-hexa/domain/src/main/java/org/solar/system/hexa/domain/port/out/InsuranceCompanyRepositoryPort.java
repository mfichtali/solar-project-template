package org.solar.system.hexa.domain.port.out;

import org.solar.system.hexa.domain.model.insurance.InsuranceCompanyDomain;

import java.util.Optional;

public interface InsuranceCompanyRepositoryPort {
    Optional<InsuranceCompanyDomain> findByNameOrIdentifier(String companyName, String companyIdentifier);
}
