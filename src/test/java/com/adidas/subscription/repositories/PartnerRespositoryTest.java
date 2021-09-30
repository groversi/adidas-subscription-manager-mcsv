package com.adidas.subscription.repositories;

import com.adidas.subscription.entity.Subscription;
import com.mongodb.client.MongoClients;
import com.adidas.subscription.testUtils.MockFactory;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@Validated
@ActiveProfiles(value = "test")
@TestPropertySource(locations="classpath:application.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class PartnerRespositoryTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    private MockFactory mockFactory;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void setup() throws IOException {
        mockFactory = MockFactory.getMockFactory();
        Subscription subscription = mockFactory.getSubscriptionMock();
        mongoTemplate.dropCollection("subscription");
        mongoTemplate.save(subscription);
    }

    @Test
    public void testShouldSaveASubscriptionToMongo(){
        Subscription subscription = mockFactory.getSubscriptionMock();
        subscription.setSubscriptionId("5cb825e566135255e0bf38a4");
        subscriptionRepository.save(subscription);

        Subscription returnOfRepository = mongoTemplate.findById(new ObjectId(subscription.getSubscriptionId()), Subscription.class);

        Assert.assertNotNull(returnOfRepository);
        Assert.assertTrue("Expect the same object of what was saved on mongo", new ReflectionEquals(returnOfRepository).matches(subscription));
    }

    @Test
    public void testShouldFindAPatternById6153f0597d0b9637eb4e7028(){
        Subscription subscription = mockFactory.getSubscriptionMock();

        Optional<Subscription> returnOfRepository = subscriptionRepository.findById("6153f0597d0b9637eb4e7028");

        Assert.assertTrue(returnOfRepository.isPresent());
        Assert.assertTrue("Expect the same object of what was saved on mongo", new ReflectionEquals(returnOfRepository.get()).matches(subscription));
    }

    @Test
    public void testShouldReturnAListOfSubscriptions(){
        Subscription subscription = mockFactory.getSubscriptionMock();

        List<Subscription> returnOfRepository = subscriptionRepository.findAll();

        Assert.assertFalse(returnOfRepository.isEmpty());
        Assert.assertTrue("Expect the same object of what was saved on mongo", new ReflectionEquals(returnOfRepository.get(0)).matches(subscription));
    }

    @Test
    public void testShouldFindAnEnabledSubscriptionByEmail(){
        Subscription subscription = mockFactory.getSubscriptionMock();

        Optional<Subscription> returnOfRepository = subscriptionRepository.findByEmailAndEnabled("jose.abrel@gmail.com", Boolean.TRUE);

        Assert.assertTrue(returnOfRepository.isPresent());
        Assert.assertTrue("Expect the same object of what was saved on mongo", new ReflectionEquals(returnOfRepository.get()).matches(subscription));
    }

    @Test
    public void testShouldNotFindADisabledSubscriptionByEmail(){
        Subscription subscription = mockFactory.getSubscriptionMock();
        subscription.setEnabled(Boolean.FALSE);
        mongoTemplate.save(subscription);

        Optional<Subscription> returnOfRepository = subscriptionRepository.findByEmailAndEnabled("jose.abrel@gmail.com", Boolean.TRUE);

        Assert.assertFalse(returnOfRepository.isPresent());
    }
}
