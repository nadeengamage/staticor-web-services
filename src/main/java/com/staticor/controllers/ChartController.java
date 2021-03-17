package com.staticor.controllers;

import com.staticor.models.metrics.Chart;
import com.staticor.services.ChartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class ChartController extends Response {

    private final static Logger LOGGER = LogManager.getLogger(ChartController.class);

    private final ChartService service;

    @Autowired
    public ChartController(ChartService service) {
        this.service = service;
    }

    @PostMapping(value = "/chart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody Chart chart) {

        if (Objects.isNull(chart)) {
            return badRequest("Request body is required!");
        }

        LOGGER.info("Start create chart!");

        return response(service.createChart(chart));
    }
}
