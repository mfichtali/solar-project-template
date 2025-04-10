package org.solar.system.rental.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchitectureRulesTest {

    private final JavaClasses classes = new ClassFileImporter().importPackages("org.solar.system.hexa.domain");

    @Test
    void domainShouldNotDependOnBusiness() {
        ArchRule rule = noClasses()
                .that().resideInAnyPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..business..", "..infrastructure..");
        rule.check(classes);
    }
}
