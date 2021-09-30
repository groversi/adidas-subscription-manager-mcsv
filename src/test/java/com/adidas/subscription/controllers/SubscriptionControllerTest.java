package com.adidas.subscription.controllers;

import com.adidas.subscription.dto.SubscriptionDTO;
import com.adidas.subscription.entity.Subscription;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.adidas.subscription.services.SubscriptionService;
import com.adidas.subscription.testUtils.MockFactory;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionControllerTest {

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
    public void testShouldReturnASubscriptionWithHttpStatusCode201WhenSubscriptionCreationIsRequested() throws InterruptedException {
        Subscription subscription = mockFactory.getSubscriptionMock();
        SubscriptionDTO subscriptionDTO = mockFactory.getSubscriptionDTOMock();
        when(subscriptionService.createSubscription(any(Subscription.class))).thenReturn(subscription);

        ResponseEntity<Subscription> responseEntity = partnerController.createSubscription(subscriptionDTO);

        Assert.assertEquals("Controller should return HTTP Status 201", responseEntity.getStatusCode(), HttpStatus.CREATED);
        Assert.assertTrue("Controller should return the same subscription from service",
                new ReflectionEquals(responseEntity.getBody()).matches(subscription));

    }

    @Test
    public void testShouldReturnASubscriptionWithHttpStatusCode200WhenSubscriptionGetIsRequested() throws InterruptedException {
        Subscription subscription = mockFactory.getSubscriptionMock();
        when(subscriptionService.findSubscriptionById(subscription.getSubscriptionId())).thenReturn(subscription);

        ResponseEntity<Subscription> responseEntity = partnerController.getSubscriptionById(subscription.getSubscriptionId());

        Assert.assertEquals("Controller should return HTTP Status 200", responseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertTrue("Controller should return the same subscription from service",
                new ReflectionEquals(responseEntity.getBody()).matches(subscription));

    }

    @Test
    public void testShouldReturnASubscriptionWithHttpStatusCode404WhenNonExistSubscriptionGetIsRequested() throws InterruptedException {
        Subscription subscription = mockFactory.getSubscriptionMock();
        when(subscriptionService.findSubscriptionById(subscription.getSubscriptionId())).thenReturn(null);

        ResponseEntity<Subscription> responseEntity = partnerController.getSubscriptionById(subscription.getSubscriptionId());

        Assert.assertEquals("Controller should return HTTP Status 404", responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void testShouldReturnAllSubscriptionsWithHttpStatusCode200WhenAllSubscriptionsGetIsRequested() {
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(mockFactory.getSubscriptionMock());
        subscriptions.add(mockFactory.getSubscriptionMock());
        subscriptions.add(mockFactory.getSubscriptionMock());
        when(subscriptionService.findAllSubscriptions()).thenReturn(subscriptions);

        ResponseEntity<List<Subscription>> responseEntity = partnerController.getAllSubscriptions();

        Assert.assertEquals("Controller should return HTTP Status 200", responseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertTrue("Controller should return the same subscriptions from service",
                new ReflectionEquals(responseEntity.getBody()).matches(subscriptions));

    }

    @Test
    public void testShouldReturnWithHttpStatusCode404WhenAllSubscriptionsGetIsRequested() {
        List<Subscription> subscriptions = new ArrayList<>();
        when(subscriptionService.findAllSubscriptions()).thenReturn(subscriptions);

        ResponseEntity<List<Subscription>> responseEntity = partnerController.getAllSubscriptions();

        Assert.assertEquals("Controller should return HTTP Status 200", responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void testShouldReturnWithHttpStatusCode200WhenASubscriptionDeleteIsRequested() {
        Subscription subscription = mockFactory.getSubscriptionMock();

        ResponseEntity<?> responseEntity = partnerController.deleteSubscription(subscription.getSubscriptionId());
        verify(subscriptionService, times(1)).cancelSubscription(subscription.getSubscriptionId());
        Assert.assertEquals("Controller should return HTTP Status 200", responseEntity.getStatusCode(), HttpStatus.OK);

    }
}
