package com.staticor.controllers;

import com.staticor.models.dtos.CollectionInitialization;
import com.staticor.services.CollectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class CollectionController extends Response {

    private final static Logger LOGGER = LogManager.getLogger(CollectionController.class);

    private final CollectionService service;

    public CollectionController(CollectionService service) {
        this.service = service;
    }

    @PostMapping("/collections")
    public ResponseEntity<Object> create(@RequestBody CollectionInitialization collection) {

        if (Objects.isNull(collection)) {
            return badRequest("Request body is required!");
        }

        LOGGER.info("Start create collection!");

        return response(service.createCollection(collection));
    }

    @GetMapping("/collections-by-user-id/{id}")
    public ResponseEntity<Object> getByUserId(@PathVariable String id) {

        if (Objects.isNull(id)) {
            return badRequest("Request body is required!");
        }

        LOGGER.info("Start create collection!");

        return response(service.getCollectionByUserId(id));

    }
}
