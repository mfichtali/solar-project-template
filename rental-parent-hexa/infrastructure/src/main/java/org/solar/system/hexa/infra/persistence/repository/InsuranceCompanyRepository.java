package org.solar.system.hexa.infra.persistence.repository;

import org.solar.system.hexa.infra.persistence.entity.insurance.InsuranceCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface InsuranceCompanyRepository extends JpaRepository<InsuranceCompany, UUID> {

    @Query("""
             SELECT COUNT(1) > 0
             FROM InsuranceCompany ic
             WHERE (:identifier IS NULL OR ic.companyIdentifier = :identifier)
             AND (:name IS NULL OR ic.name = :name)
            """)
    boolean existByIdentifierOrName(@Param("identifier") String identifier, @Param("name") String name);

    @Query("SELECT COUNT(1) > 0 FROM InsuranceCompany")
    boolean isNotEmpty();

    @Query("""
             SELECT ic FROM InsuranceCompany ic
             WHERE (:name IS NULL OR ic.name = :name)
             AND  (:identifier IS NULL OR ic.companyIdentifier = :identifier)
            """)
    Optional<InsuranceCompany> findByNameOrIdentifier(@Param("name") final String companyName, @Param("identifier") final String companyIdentifier);

}
