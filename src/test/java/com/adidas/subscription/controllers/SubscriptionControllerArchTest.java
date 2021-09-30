package com.adidas.subscription.controllers;

import com.adidas.subscription.dto.SubscriptionDTO;
import com.adidas.subscription.entity.Subscription;
import com.adidas.subscription.services.SubscriptionService;
import com.adidas.subscription.testUtils.MockFactory;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionControllerArchTest {

    private MockFactory mockFactory;

    @InjectMocks
    private SubscriptionController partnerController;

    @Mock
    private SubscriptionService subscriptionService;

    @Before
    public void Setup(){
        mockFactory = MockFactory.getMockFactory();
    }

    @Test
    public void controllersShouldBeSuffixed() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.adidas.subscription.controllers");
        ArchRule rule = classes()
                .that()
                .resideInAPackage("..controllers..")
                .and()
                .haveSimpleNameNotEndingWith("Test")
                .should()
                .haveSimpleNameEndingWith("Controller");

        rule.check(importedClasses);
    }

    @Test
    public void controllersShouldBeAnnotatedWithRestController() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.adidas.subscription.controllers");
        ArchRule rule = classes()
                .that()
                .resideInAPackage("..controllers..")
                .and()
                .haveSimpleNameNotEndingWith("Test")
                .should()
                .beAnnotatedWith(RestController.class);

        rule.check(importedClasses);
    }

    @Test
    public void controllersShouldBeAnnotatedWithRequestMappping() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.adidas.subscription.controllers");
        ArchRule rule = classes()
                .that()
                .resideInAPackage("..controllers..")
                .and()
                .haveSimpleNameNotEndingWith("Test")
                .should()
                .beAnnotatedWith(RequestMapping.class);

        rule.check(importedClasses);
    }

    @Test
    public void getSubscriptionByIdShouldHaveGetMappingAnnotation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.adidas.subscription.controllers");
        ArchRule rule = methods()
                .that()
                .haveName("getSubscriptionById")
                .should()
                .beAnnotatedWith(GetMapping.class);

        rule.check(importedClasses);
    }

    @Test
    public void getAllSubscriptionsShouldHaveGetMappingAnnotation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.adidas.subscription.controllers");
        ArchRule rule = methods()
                .that()
                .haveName("getAllSubscriptions")
                .should()
                .beAnnotatedWith(GetMapping.class);

        rule.check(importedClasses);
    }

    @Test
    public void createSubscriptionShouldHaveGetMappingAnnotation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.adidas.subscription.controllers");
        ArchRule rule = methods()
                .that()
                .haveName("createSubscription")
                .should()
                .beAnnotatedWith(PostMapping.class);

        rule.check(importedClasses);
    }

    @Test
    public void deleteSubscriptionShouldHaveGetMappingAnnotation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.adidas.subscription.controllers");
        ArchRule rule = methods()
                .that()
                .haveName("deleteSubscription")
                .should()
                .beAnnotatedWith(DeleteMapping.class);

        rule.check(importedClasses);
    }
}
