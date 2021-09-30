package com.adidas.subscription.entity;

import com.adidas.subscription.exceptions.customs.EntityNotFoundException;
import com.adidas.subscription.exceptions.customs.InvalidRequestException;
import com.adidas.subscription.integration.EmailIntegration;
import com.adidas.subscription.repositories.SubscriptionRepository;
import com.adidas.subscription.services.impl.SubscriptionServiceImpl;
import com.adidas.subscription.testUtils.MockFactory;
import com.adidas.subscription.util.GenderEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionTest {

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
    public void testShouldCreateAValidSubscription(){
        Subscription subscription = new Subscription(
                "some@email.com",
                "some_name",
                "Male",
                "1991-12-26",
                true,
                1);

        Assert.assertEquals("some@email.com", subscription.getEmail());
        Assert.assertEquals("some_name", subscription.getFirstName());
        Assert.assertEquals(GenderEnum.MALE, subscription.getGender());
        Assert.assertEquals(LocalDate.parse("1991-12-26", DateTimeFormatter.ofPattern("yyyy-MM-dd")), subscription.getDateOfBirth());
        Assert.assertEquals(Boolean.TRUE, subscription.getEnabled());
        Assert.assertEquals(Integer.valueOf(1), subscription.getCampaignId());

    }

    @Test
    public void testShouldCreateAValidSubscriptionWithNotDeclaredGenderWhenGenderIsNotInformed(){
        Subscription subscription = new Subscription(
                "some@email.com",
                "some_name",
                null,
                "1991-12-26",
                true,
                1);

        Assert.assertEquals("some@email.com", subscription.getEmail());
        Assert.assertEquals("some_name", subscription.getFirstName());
        Assert.assertEquals(GenderEnum.NOT_DECLARED, subscription.getGender());
        Assert.assertEquals(LocalDate.parse("1991-12-26", DateTimeFormatter.ofPattern("yyyy-MM-dd")), subscription.getDateOfBirth());
        Assert.assertEquals(Boolean.TRUE, subscription.getEnabled());
        Assert.assertEquals(Integer.valueOf(1), subscription.getCampaignId());

    }

    @Test
    public void testShouldCreateAValidSubscriptionWithNoFirstNameWhenGenderAndFirstNameIsNotInformed(){
        Subscription subscription = new Subscription(
                "some@email.com",
                null,
                "",
                "1991-12-26",
                true,
                1);

        Assert.assertEquals("some@email.com", subscription.getEmail());
        Assert.assertEquals(null, subscription.getFirstName());
        Assert.assertEquals(GenderEnum.NOT_DECLARED, subscription.getGender());
        Assert.assertEquals(LocalDate.parse("1991-12-26", DateTimeFormatter.ofPattern("yyyy-MM-dd")), subscription.getDateOfBirth());
        Assert.assertEquals(Boolean.TRUE, subscription.getEnabled());
        Assert.assertEquals(Integer.valueOf(1), subscription.getCampaignId());

    }

    @Test
    public void testShouldThrowInvalidRequestExceptionWhenEmailIsNotValid(){
        assertThatThrownBy(() -> {
            Subscription subscription = new Subscription(
                    "someemail.com",
                    "some_name",
                    "Male",
                    "1991-12-26",
                    true,
                    1);


            subscriptionService.createSubscription(subscription);


        }) .isInstanceOf(InvalidRequestException.class)
           .hasMessage("Invalid email address");

    }

    @Test
    public void testShouldThrowInvalidRequestExceptionWhenDateOfBirthIsInvalid(){
        assertThatThrownBy(() -> {
            Subscription subscription = new Subscription(
                    "some@email.com",
                    "some_name",
                    "Male",
                    "1991-13-26",
                    true,
                    1);


            subscriptionService.createSubscription(subscription);


        }) .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Invalid date of birth");

    }

    @Test
    public void testShouldThrowInvalidRequestExceptionWhensubscriptionConsentIsInvalid(){
        assertThatThrownBy(() -> {
            Subscription subscription = new Subscription(
                    "some@email.com",
                    "some_name",
                    "Male",
                    "1991-12-26",
                    null,
                    1);


            subscriptionService.createSubscription(subscription);


        }) .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Must answer newsletter terms");

    }

    @Test
    public void testShouldThrowInvalidRequestExceptionWhenCampaignIdIsnull(){
        assertThatThrownBy(() -> {
            Subscription subscription = new Subscription(
                    "some@email.com",
                    "some_name",
                    "Male",
                    "1991-12-26",
                    true,
                    null);


            subscriptionService.createSubscription(subscription);


        }) .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Must inform campaign id");

    }

}
