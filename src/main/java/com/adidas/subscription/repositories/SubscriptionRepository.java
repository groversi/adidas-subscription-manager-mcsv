package com.adidas.subscription.repositories;


import com.adidas.subscription.entity.Subscription;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

    @Override
    @Cacheable("subscription")
    Optional<Subscription> findById(String integer);

    @Override
    @Cacheable("subscription")
    <S extends Subscription> S save(S s);

    @Override
    @Cacheable("subscription")
    List<Subscription> findAll();

    @Cacheable("subscription")
    Optional<Subscription> findByEmailAndEnabled(String email, Boolean enabled);


}
