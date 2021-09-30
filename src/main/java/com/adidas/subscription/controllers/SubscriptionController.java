package com.adidas.subscription.controllers;

import com.adidas.subscription.dto.SubscriptionDTO;
import com.adidas.subscription.entity.Subscription;
import com.adidas.subscription.services.SubscriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@Api(tags = "Manage subscription API")
public class SubscriptionController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SubscriptionController.class);

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping(value = "/subscription/{subscriptionId}")
    @ApiOperation(value = "Search a subscription by id")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true)
    public ResponseEntity<Subscription> getSubscriptionById(
            @Valid @PathVariable
            @NotEmpty(message = "Id is required")
            String subscriptionId
    ) {

        log.info("Search subscription by id request received");
        Subscription subscription = subscriptionService.findSubscriptionById(subscriptionId);
        if(subscription == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @GetMapping(value = "/subscription")
    @ApiOperation(value = "List all subscriptions")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true)
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {

        log.info("Get all subscriptions request received");
        List<Subscription> subscriptions = subscriptionService.findAllSubscriptions();
        if(subscriptions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @PostMapping(value = "/subscription")
    @ApiOperation(value = "Create a subscription ")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true)
    public ResponseEntity<Subscription> createSubscription(
            @RequestBody SubscriptionDTO subscriptionDTO) throws InterruptedException {
        log.info("Create a subscription request received");
        Subscription subscription = new Subscription(
                subscriptionDTO.getEmail(),
                subscriptionDTO.getFirstName(),
                subscriptionDTO.getGender(),
                subscriptionDTO.getDateOfBirth(),
                subscriptionDTO.getSubscriptionConsent(),
                subscriptionDTO.getCampaignId()) ;

        return new ResponseEntity<>(subscriptionService.createSubscription(subscription), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/subscription/{subscriptionId}")
    @ApiOperation(value = "Cancel a subscription ")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true)
    public ResponseEntity<?> deleteSubscription(
            @Valid @PathVariable
            @NotEmpty(message = "Id is required")
                    String subscriptionId) {

        log.info("Delete subscription request received");
        subscriptionService.cancelSubscription(subscriptionId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
