package com.ftr.dgb.payments.action.catalog;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.ftr.dgb.payments.action.catalog");

        noClasses()
            .that()
            .resideInAnyPackage("com.ftr.dgb.payments.action.catalog.service..")
            .or()
            .resideInAnyPackage("com.ftr.dgb.payments.action.catalog.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.ftr.dgb.payments.action.catalog.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
