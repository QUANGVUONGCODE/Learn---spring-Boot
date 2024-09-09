package com.api.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dbo.request.ApiRequest;
import com.api.dbo.request.AutheticationRequest;
import com.api.dbo.request.IntrospectRequest;
import com.api.dbo.response.AuthenticationReponse;
import com.api.dbo.response.IntrospectReponse;
import com.api.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiRequest<AuthenticationReponse> authenticate(@RequestBody AutheticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiRequest.<AuthenticationReponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    public ApiRequest<IntrospectReponse> authenticate(@RequestBody IntrospectRequest request)
            throws JOSEException, ParseException {
        var result = authenticationService.introspect(request);
        return ApiRequest.<IntrospectReponse>builder()
                .result(result)
                .build();
    }
}
