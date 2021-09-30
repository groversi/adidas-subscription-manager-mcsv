package com.adidas.subscription.filters;

import com.adidas.subscription.interceptor.AuthorizationInterceptor;
import com.adidas.subscription.services.AuthenticationService;
import com.google.common.net.HttpHeaders;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import static org.mockito.Mockito.*;

import javax.servlet.ServletException;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationFilterTest {

    private MockHttpServletRequest requestMock;
    private MockHttpServletResponse responseMock;
    private MockFilterChain chainMock;
    private String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;
    private String AUTHORIZATION_HEADER_VALUE;

    @InjectMocks
    private AuthorizationInterceptor authorizationInterceptor;

    @Mock
    private AuthenticationService authenticationService;

    @Before
    public void Setup(){
        requestMock = new MockHttpServletRequest();
        responseMock = new MockHttpServletResponse();
        chainMock = new MockFilterChain();
    }

    @Test
    public void testShouldCheckIfFilterDenyAccessWhenAuthHeaderIsNotPassed() throws IOException, ServletException {
        authorizationInterceptor.doFilterInternal(requestMock, responseMock, chainMock);

        Assert.assertEquals("Filter should deny access", responseMock.getStatus(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testShouldCheckIfFilterDenyAccessWhenWrongAuthHeaderIsNotPassed() throws IOException, ServletException {
        requestMock.addHeader(AUTHORIZATION_HEADER, "someJWT");
        when(authenticationService.validateJwtToken(anyString())).thenReturn(Boolean.FALSE);
        authorizationInterceptor.doFilterInternal(requestMock, responseMock, chainMock);

        Assert.assertEquals("Filter should set Header value", responseMock.getStatus(), HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void testShouldCheckIfFilterDenyAccessWhenWrongAuthHeaderIsNotPassed2() throws IOException, ServletException {
        requestMock.addHeader(AUTHORIZATION_HEADER, "someJWT");
        when(authenticationService.validateJwtToken(anyString())).thenReturn(Boolean.TRUE);
        authorizationInterceptor.doFilterInternal(requestMock, responseMock, chainMock);

        Assert.assertEquals("Request has to be on chain", requestMock, chainMock.getRequest());
        Assert.assertEquals("Response has to be on chain", responseMock, chainMock.getResponse());
    }
}
