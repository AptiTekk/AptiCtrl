/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.rest.controllers.api;

import com.aptitekk.aptictrl.core.domain.rest.RestError;
import com.aptitekk.aptictrl.core.services.LogService;
import com.aptitekk.aptictrl.core.services.auth.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collections;

@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
public abstract class APIControllerAbstract {

    final static String[] ACCEPTED_TIME_FORMATS = {"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm", "yyyy-MM-dd"};
    final static String VALID_CHARACTER_PATTERN = "[^<>;=]*";

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    AuthService authService;

    @Autowired
    LogService logService;

    APIControllerAbstract() {
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
    }

    ResponseEntity<?> ok(Object entity) {
        return ResponseEntity.ok(entity);
    }

    ResponseEntity<?> created(Object entity, String path) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        //Ensure that the path starts with /api/*
        if (!path.startsWith("/api") && !path.startsWith("api")) {
            if (path.startsWith("/"))
                path = "/api" + (path.startsWith("/") ? "" : "/") + path;
        } else if (path.startsWith("api"))
            path = "/" + path;

        String uriString = ServletUriComponentsBuilder.fromCurrentContextPath().path(path).build().toUriString();

        headers.put("Location", Collections.singletonList(uriString));

        return new ResponseEntity<>(entity, headers, HttpStatus.CREATED);
    }

    ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }

    ResponseEntity<Object> badRequest() {
        return badRequest("");
    }

    ResponseEntity<Object> badRequest(String message) {
        return createErrorResponseEntity(message, HttpStatus.BAD_REQUEST);
    }

    ResponseEntity<Object> unauthorized() {
        return unauthorized("");
    }

    ResponseEntity<Object> unauthorized(String message) {
        return createErrorResponseEntity(message, HttpStatus.UNAUTHORIZED);
    }

    ResponseEntity<Object> noPermission() {
        return createErrorResponseEntity("You do not have permission.", HttpStatus.UNAUTHORIZED);
    }

    ResponseEntity<Object> noPermission(String message) {
        return createErrorResponseEntity(message, HttpStatus.UNAUTHORIZED);
    }

    ResponseEntity<Object> serverError() {
        return serverError("Internal Server Error");
    }

    ResponseEntity<Object> serverError(String message) {
        return createErrorResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    ResponseEntity<?> notImplemented() {
        return notImplemented("This is not available.");
    }

    ResponseEntity<Object> notImplemented(String message) {
        return createErrorResponseEntity(message, HttpStatus.NOT_IMPLEMENTED);
    }

    private ResponseEntity<Object> createErrorResponseEntity(String message, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(new RestError(message));
    }

}
