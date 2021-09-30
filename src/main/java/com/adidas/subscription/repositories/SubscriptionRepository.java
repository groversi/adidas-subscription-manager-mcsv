package com.adidas.subscription.repositories;


import com.adidas.subscription.entity.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

    @Override
    Optional<Subscription> findById(String integer);

    @Override
    <S extends Subscription> S save(S s);

    @Override
    List<Subscription> findAll();

    Optional<Subscription> findByEmailAndEnabled(String email, Boolean enabled);


}
