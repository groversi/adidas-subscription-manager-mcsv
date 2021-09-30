package com.adidas.subscription.integration;


import com.adidas.subscription.entity.Subscription;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "Subscription", url = "${feign.url.email.server}")
public interface EmailIntegration {

    @RequestMapping(value = "/v1/subscription/create-subscription",
            produces = {"application/json"}, method = RequestMethod.POST)
    void createSubscriptionEmail(@RequestBody Subscription subscriptionRequest);

    @RequestMapping(value = "/v1/subscription/cancel-subscription",
            produces = {"application/json"}, method = RequestMethod.POST)
    void cancelSubscriptionEmail(@RequestBody Subscription subscriptionRequest);
}
