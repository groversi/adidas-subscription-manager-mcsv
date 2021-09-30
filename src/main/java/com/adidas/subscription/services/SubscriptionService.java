package com.adidas.subscription.services;

import com.adidas.subscription.entity.Subscription;

import java.util.List;

public interface SubscriptionService {

    Subscription createSubscription(Subscription subscription);

    Subscription findSubscriptionById(String id);

    List<Subscription> findAllSubscriptions();

    void cancelSubscription(String subscriptionId);
}
