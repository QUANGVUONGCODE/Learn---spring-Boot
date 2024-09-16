package com.api.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.api.dbo.request.AutheticationRequest;
import com.api.dbo.request.IntrospectRequest;
import com.api.dbo.response.AuthenticationReponse;
import com.api.dbo.response.IntrospectReponse;
import com.api.entity.User;
import com.api.exception.AppException;
import com.api.exception.ErrorCode;
import com.api.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    UserRepository userRepository;
    @NonFinal
    @Value("${jwt.signerkey}")
    protected String SIGNER_KEY;

    public IntrospectReponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return IntrospectReponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();
    }

    public AuthenticationReponse authenticate(AutheticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = genarateToken(user);

        return AuthenticationReponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String genarateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jWTClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("devteria.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jWTClaimsSet.toJSONObject());

        JWSObject jWSObject = new JWSObject(header, payload);

        try {
            jWSObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jWSObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot crate token", e);
            throw new RuntimeException(e);
        }

    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(stringJoiner::add);
        }
        return stringJoiner.toString();
    }
}
