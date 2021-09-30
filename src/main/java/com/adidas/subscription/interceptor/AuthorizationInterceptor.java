package com.adidas.subscription.interceptor;

import com.adidas.subscription.exceptions.dto.ApiErrorResponseDTO;
import com.adidas.subscription.services.AuthenticationService;
import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import feign.RequestInterceptor;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

@Component
public class AuthorizationInterceptor extends OncePerRequestFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationInterceptor.class);
    private static final String AUTH_HEADER_PREFIX = "Bearer ";
    private String jwtToken;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        Collection<String> includePatterns = new LinkedList<>();
        includePatterns.add("v1/subscription");

        return includePatterns.stream().noneMatch(p -> request.getServletPath().contains(p));
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        this.jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(this.jwtToken  == null){
            generateResponseError(response, HttpStatus.BAD_REQUEST, "Jwt not provided");
            return;
        }

        Boolean authorized = authenticationService.validateJwtToken(this.jwtToken.replace(AUTH_HEADER_PREFIX, ""));
        if(authorized)
            filterChain.doFilter(request, response);
        else{
            generateResponseError(response, HttpStatus.UNAUTHORIZED, "Invalid access credentials");
        }
    }

    private static void generateResponseError(HttpServletResponse httpServletResponse, HttpStatus httpStatus, String message) throws IOException {
        httpServletResponse.setStatus(httpStatus.value());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(Encode.forHtmlContent(generateApiError(httpStatus, message)));
        httpServletResponse.getWriter().flush();
    }

    private static String generateApiError(HttpStatus statusCode, String message){
        return new Gson()
                .toJson(
                        new ApiErrorResponseDTO(statusCode, statusCode.getReasonPhrase(), message)
                );
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", this.jwtToken);
        };
    }
}
