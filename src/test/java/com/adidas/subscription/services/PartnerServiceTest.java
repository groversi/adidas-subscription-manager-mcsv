package com.adidas.subscription.services;

import com.adidas.subscription.dto.SubscriptionDTO;
import com.adidas.subscription.entity.Subscription;
import com.adidas.subscription.exceptions.customs.EntityNotFoundException;
import com.adidas.subscription.exceptions.customs.InvalidRequestException;
import com.adidas.subscription.integration.EmailIntegration;
import com.adidas.subscription.repositories.SubscriptionRepository;
import com.adidas.subscription.services.impl.SubscriptionServiceImpl;
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
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PartnerServiceTest {

    private MockFactory mockFactory;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private EmailIntegration emailIntegration;

    @Before
    public void Setup(){
        mockFactory = MockFactory.getMockFactory();
    }

    @Test
    public void testShouldThrowAnExceptionWhenAlreadySubscribedEmailisSent(){
        Subscription subscription = mockFactory.getSubscriptionMock();
        assertThatThrownBy(() -> {
            when(subscriptionRepository.findByEmailAndEnabled(subscription.getEmail(), Boolean.TRUE)).thenReturn(Optional.of(subscription));

            subscriptionService.createSubscription(subscription);


        }) .isInstanceOf(InvalidRequestException.class)
           .hasMessage("Email " + subscription.getEmail() + " already subscribed");

    }

    @Test
    public void testShouldReturnACreatedSubscriptionOnDataBase(){
        Subscription subscription = mockFactory.getSubscriptionMock();
        when(subscriptionRepository.findByEmailAndEnabled(subscription.getEmail(), Boolean.TRUE)).thenReturn(Optional.empty());
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);
        doNothing().when(emailIntegration).createSubscriptionEmail(subscription);

        Subscription subscriptionReturned = subscriptionService.createSubscription(subscription);

        Assert.assertTrue("Expect the same object of what was saved on database", new ReflectionEquals(subscriptionReturned).matches(subscription));

    }

    @Test
    public void testShouldReturnASubscriptionById(){
        Subscription subscription = mockFactory.getSubscriptionMock();
        when(subscriptionRepository.findById(subscription.getSubscriptionId())).thenReturn(Optional.of(subscription));

        Subscription subscriptionReturned = subscriptionService.findSubscriptionById(subscription.getSubscriptionId());

        Assert.assertTrue("Expect the same object of what was saved on database", new ReflectionEquals(subscriptionReturned).matches(subscription));

    }

    @Test
    public void testShouldReturnNullWhenSubscriptionDoNotExist(){
        Subscription subscription = mockFactory.getSubscriptionMock();
        when(subscriptionRepository.findById(subscription.getSubscriptionId())).thenReturn(Optional.empty());

        Subscription subscriptionReturned = subscriptionService.findSubscriptionById(subscription.getSubscriptionId());

        Assert.assertTrue("Expect the same object of what was saved on database", new ReflectionEquals(subscriptionReturned).matches(null));

    }

    @Test
    public void testShouldReturnAlistOfSubscriptions(){
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(mockFactory.getSubscriptionMock());
        subscriptions.add(mockFactory.getSubscriptionMock());
        subscriptions.add(mockFactory.getSubscriptionMock());

        when(subscriptionRepository.findAll()).thenReturn(subscriptions);

        List<Subscription> subscriptionReturned = subscriptionService.findAllSubscriptions();

        Assert.assertEquals("List must have 3 elements", 3, subscriptionReturned.size());
        Assert.assertTrue("Expect the same object of what was saved on database", new ReflectionEquals(subscriptionReturned.get(0)).matches(mockFactory.getSubscriptionMock()));

    }

    @Test
    public void testShouldThrowAnExceptionWhenAlreadyDeletedSubscriptionIsSent(){
        Subscription subscription = mockFactory.getSubscriptionMock();
        subscription.setEnabled(Boolean.FALSE);
        assertThatThrownBy(() -> {
            when(subscriptionRepository.findById(subscription.getSubscriptionId())).thenReturn(Optional.of(subscription));

            subscriptionService.cancelSubscription(subscription.getSubscriptionId());


        }) .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Subscription already cancelled");

    }

    @Test
    public void testShouldThrowAnExceptionWhenANonExistSubscriptionIsSent(){
        Subscription subscription = mockFactory.getSubscriptionMock();
        assertThatThrownBy(() -> {
            when(subscriptionRepository.findById(subscription.getSubscriptionId())).thenReturn(Optional.empty());

            subscriptionService.cancelSubscription(subscription.getSubscriptionId());


        }) .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Subscription not found");

    }

    @Test
    public void testShouldLogicallyDeleteASubscription(){
        Subscription subscription = mockFactory.getSubscriptionMock();
        when(subscriptionRepository.findById(subscription.getSubscriptionId())).thenReturn(Optional.of(subscription));
        //doNothing().when(emailIntegration).createSubscriptionEmail(subscription);

        subscriptionService.cancelSubscription(subscription.getSubscriptionId());

        verify(subscriptionRepository, times(1)).save(argThat(
                x -> {
                    return x.getEnabled().equals(Boolean.FALSE);
                }
        ));
    }
}
