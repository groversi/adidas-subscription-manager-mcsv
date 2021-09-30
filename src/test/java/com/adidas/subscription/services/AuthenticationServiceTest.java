package com.adidas.subscription.services;

import com.adidas.subscription.services.impl.AuthenticationServiceImpl;
import com.adidas.subscription.testUtils.MockFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

    private MockFactory mockFactory;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Before
    public void Setup(){
        mockFactory = MockFactory.getMockFactory();
    }

    @Test
    public void testShouldRefuseAnInvalidToken() {
        Assert.assertFalse(authenticationService.validateJwtToken("someToken"));
    }



}
