package com.staticor.controllers;

import com.staticor.services.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

public abstract class Response {

    protected ResponseEntity<Object> response(String message) {
        return ResponseEntity.ok().body(Collections.singletonMap("message", message));
    }

    protected ResponseEntity<Object> response(String code, Object object) {
        return ResponseEntity.ok().body(Map.of("code", code, "data", object));
    }

    protected ResponseEntity<Object> response(Map<String, Object> map) {
        return ResponseEntity.ok().body(map);
    }

    protected ResponseEntity<Object> response(ServiceResponse response) {
        return ResponseEntity.status(response.getCode()).body(response);
    }

    protected ResponseEntity<Object> badRequest(Object object) {
        return ResponseEntity.badRequest().body(object);
    }

    protected ResponseEntity<Object> error(Object object) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", object));
    }
}
