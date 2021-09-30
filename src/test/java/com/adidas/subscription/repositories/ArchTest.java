package com.adidas.subscription.repositories;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.Test;
import org.springframework.stereotype.Repository;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;


public class ArchTest {

    @Test
    public void serviceShouldBeSuffixed() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.adidas.subscription.repositories");
        ArchRule rule = classes()
                .that()
                .resideInAPackage("..repositories..")
                .and()
                .haveSimpleNameNotEndingWith("Impl")
                .and()
                .haveSimpleNameNotEndingWith("Test")
                .should()
                .haveSimpleNameEndingWith("Repository");

        rule.check(importedClasses);
    }

    @Test
    public void serviceImplShouldBeAnnotatedWithRestService() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.adidas.subscription.repositories");
        ArchRule rule = classes()
                .that()
                .resideInAPackage("..repositories..")
                .and()
                .haveSimpleNameNotEndingWith("Test")
                .should()
                .beAnnotatedWith(Repository.class);

        rule.check(importedClasses);
    }

}
