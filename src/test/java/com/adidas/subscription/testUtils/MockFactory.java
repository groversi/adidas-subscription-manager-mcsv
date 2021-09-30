package com.adidas.subscription.testUtils;

import com.adidas.subscription.dto.SubscriptionDTO;
import com.adidas.subscription.entity.Subscription;
import com.adidas.subscription.util.GenderEnum;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MockFactory {

    private MockFactory(){}

    public static MockFactory getMockFactory(){return new MockFactory(); }

    public Subscription getSubscriptionMock(){
        Subscription subscription = new Subscription();
        subscription.setSubscriptionId("6153f0597d0b9637eb4e7028");
        subscription.setFirstName("Jose");
        subscription.setEnabled(true);
        subscription.setEmail("jose.abrel@gmail.com");
        subscription.setGender(GenderEnum.MALE);
        subscription.setDateOfBirth(LocalDate.parse("1991-12-26", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        subscription.setSubscriptionConsent(true);
        subscription.setCampaignId(1);
        return subscription;

    }

    public SubscriptionDTO getSubscriptionDTOMock(){
        return new Gson().fromJson(
                "{\n" +
                        "    \"email\": \"jose.abrel@gmail.com\",\n" +
                        "    \"firstName\": \"Jose\",\n" +
                        "    \"gender\": \"Male\",\n" +
                        "    \"dateOfBirth\": \"1991-12-26\",\n" +
                        "    \"subscriptionConsent\": true,\n" +
                        "    \"campaignId\": 1\n" +
                        "}", SubscriptionDTO.class
        );
    }
}
