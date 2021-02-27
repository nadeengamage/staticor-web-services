package com.staticor.controllers;

import com.staticor.models.dtos.ReportCreateDto;
import com.staticor.models.dtos.ReportEditorDto;
import com.staticor.services.ReportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class ReportController extends Response {

    private final static Logger LOGGER = LogManager.getLogger(ReportController.class);

    private final ReportService service;

    @Autowired
    public ReportController(ReportService service) {
        this.service = service;
    }

    @PostMapping("/reports/editor")
    public ResponseEntity<Object> editor(@RequestBody ReportEditorDto editor) {

            if (Objects.isNull(editor)) {
                return badRequest("Request body is required!");
            }

            LOGGER.info("Start querying sql!");
        return response(service.executeSqlQuery(editor));
    }

    @PostMapping("/reports/create-new-report")
    public ResponseEntity<Object> createNewReport(@RequestBody ReportCreateDto createDto) {

        if (Objects.isNull(createDto)) {
            return badRequest("Request body is required!");
        }

        LOGGER.info("Start querying sql!");
        return response(service.createNewReport(createDto));
    }
}
