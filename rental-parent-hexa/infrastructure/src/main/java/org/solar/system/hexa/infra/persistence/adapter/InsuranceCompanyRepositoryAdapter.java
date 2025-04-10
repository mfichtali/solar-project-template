package org.solar.system.hexa.infra.persistence.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.solar.system.hexa.domain.model.insurance.InsuranceCompanyDomain;
import org.solar.system.hexa.domain.port.out.InsuranceCompanyRepositoryPort;
import org.solar.system.hexa.infra.mapper.GlobalMapper;
import org.solar.system.hexa.infra.persistence.repository.InsuranceCompanyRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class InsuranceCompanyRepositoryAdapter implements InsuranceCompanyRepositoryPort {

    private final GlobalMapper mapper;
    private final InsuranceCompanyRepository insuranceCompanyRepository;

    @Override
    public Optional<InsuranceCompanyDomain> findByNameOrIdentifier(final String companyName, final String companyIdentifier) {
        return insuranceCompanyRepository.findByNameOrIdentifier(companyName, companyIdentifier)
                .map(mapper::insuranceCompanyToDomain);
    }
}
