package com.adidas.subscription.services.impl;

import com.adidas.subscription.entity.Subscription;
import com.adidas.subscription.exceptions.customs.EntityNotFoundException;
import com.adidas.subscription.exceptions.customs.InvalidRequestException;
import com.adidas.subscription.integration.EmailIntegration;
import com.adidas.subscription.repositories.SubscriptionRepository;
import com.adidas.subscription.services.SubscriptionService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SubscriptionServiceImpl.class);

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private EmailIntegration emailIntegration;

    @Override
    @CacheEvict(value = "subscription", allEntries = true)
    public Subscription createSubscription(Subscription subscription) {
        log.info("Creating subscription on database");
        if(subscriptionRepository.findByEmailAndEnabled(subscription.getEmail(), Boolean.TRUE).isPresent()){
            log.warn("Email {} already subscribed", subscription.getEmail());
            throw new InvalidRequestException("Email " + subscription.getEmail() + " already subscribed");
        }
        Subscription subscriptionResponse = subscriptionRepository.save(subscription);
        log.info("Sending subscription creation email");
        emailIntegration.createSubscriptionEmail(subscriptionResponse);
        return subscriptionResponse;
    }

    @Override
    public Subscription findSubscriptionById(String id) {
        log.info("Searching subscription on database");
        Optional<Subscription> subscriptionOpt = subscriptionRepository.findById(id);
        return subscriptionOpt.orElse(null);
    }

    @Override
    public List<Subscription> findAllSubscriptions() {
        log.info("Searching all subscriptions on database");
        return subscriptionRepository.findAll();
    }

    @Override
    @CacheEvict(value = "subscription", allEntries = true)
    public void cancelSubscription(String subscriptionId) {
        log.info("Searching subscription on database");
        Optional<Subscription> subscriptionOpt = subscriptionRepository.findById(subscriptionId);
        if (subscriptionOpt.isPresent()) {
            if(subscriptionOpt.get().getEnabled().equals(Boolean.FALSE)) {
                log.warn("Subscription {} already cancelled", subscriptionId);
                throw new InvalidRequestException("Subscription already cancelled");
            }
            Subscription subscription = subscriptionOpt.get();
            subscription.setEnabled(Boolean.FALSE);

            log.info("Sending subscription cancellation email");
            emailIntegration.cancelSubscriptionEmail(subscription);
            subscriptionRepository.save(subscription);
        } else {
            log.warn("Subscription {} not found", subscriptionId);
            throw new EntityNotFoundException("Subscription not found");
        }
    }
}
