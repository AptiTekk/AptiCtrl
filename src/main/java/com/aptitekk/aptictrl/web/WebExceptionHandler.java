/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.web;

import com.aptitekk.aptictrl.core.domain.rest.RestError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler extends ResponseEntityExceptionHandler {

    private final ResourceLoader resourceLoader;

    @Autowired
    public WebExceptionHandler(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex.getRequestURL().startsWith("/api"))
            // For API calls, send a rest error.
            return new ResponseEntity<>(new RestError("The URL you have reached is not in service at this time. (404)"), HttpStatus.NOT_FOUND);
        else {
            // For non-API calls, try to load the resource they asked for
            Resource resource = this.resourceLoader.getResource(ex.getRequestURL());

            // If it doesn't exist, load index.html
            if (!resource.exists())
                resource = this.resourceLoader.getResource("index.html");

            // Send the resource.
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new RestError("An Internal Server Error occurred while processing your request. (500)"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
