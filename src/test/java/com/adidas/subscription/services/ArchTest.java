package com.adidas.subscription.services;

import com.adidas.subscription.repositories.SubscriptionRepository;
import com.adidas.subscription.services.impl.SubscriptionServiceImpl;
import com.adidas.subscription.testUtils.MockFactory;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@RunWith(MockitoJUnitRunner.class)
public class ArchTest {

    private MockFactory mockFactory;

    @InjectMocks
    private SubscriptionServiceImpl partnerService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Before
    public void Setup(){
        mockFactory = MockFactory.getMockFactory();
    }

    @Test
    public void serviceShouldBeSuffixed() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.adidas.subscription.services");
        ArchRule rule = classes()
                .that()
                .resideInAPackage("..services..")
                .and()
                .haveSimpleNameNotEndingWith("Impl")
                .and()
                .haveSimpleNameNotEndingWith("Test")
                .should()
                .haveSimpleNameEndingWith("Service");

        rule.check(importedClasses);
    }

    @Test
    public void serviceImplShouldBeSuffixed() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.adidas.subscription.impl");
        ArchRule rule = classes()
                .that()
                .resideInAPackage("..services..")
                .and()
                .haveSimpleNameNotEndingWith("Test")
                .should()
                .haveSimpleNameEndingWith("ServiceImpl");

        rule.check(importedClasses);
    }

    @Test
    public void serviceImplShouldBeAnnotatedWithRestService() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.adidas.subscription.impl");
        ArchRule rule = classes()
                .that()
                .resideInAPackage("..services..")
                .and()
                .haveSimpleNameNotEndingWith("Test")
                .should()
                .beAnnotatedWith(Service.class);

        rule.check(importedClasses);
    }

}
