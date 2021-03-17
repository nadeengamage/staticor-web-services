package com.staticor.controllers;

import com.staticor.models.dtos.ReportCreateDto;
import com.staticor.models.dtos.ReportEditorDto;
import com.staticor.models.dtos.ViewReportDto;
import com.staticor.services.ReportService;
import com.staticor.services.ResourceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class ReportController extends Response {

    private final static Logger LOGGER = LogManager.getLogger(ReportController.class);

    private final ReportService service;

    private final ResourceService resourceService;

    @Autowired
    public ReportController(ReportService service, ResourceService resourceService) {
        this.service = service;
        this.resourceService = resourceService;
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

    @PostMapping("/reports/view-report")
    public ResponseEntity<Object> createNewReport(@RequestBody ViewReportDto reportDto) {

        if (Objects.isNull(reportDto)) {
            return badRequest("Request body is required!");
        }

        LOGGER.info("Start querying sql!");
        return response(service.viewReport(reportDto));
    }

    @GetMapping("/reports/get-report-by-id/{id}")
    public ResponseEntity<Object> getReportById(@PathVariable Long id) {

        if (Objects.isNull(id)) {
            return badRequest("Request body is required!");
        }

        LOGGER.info("Start querying sql!");
        return response(service.getReportById(id));
    }

    @PostMapping("/reports/update-report")
    public ResponseEntity<Object> updateReport(@RequestBody ReportCreateDto createDto) {

        if (Objects.isNull(createDto)) {
            return badRequest("Request body is required!");
        }

        LOGGER.info("Start querying sql!");
        return response(service.updateReport(createDto));
    }

    @DeleteMapping("/reports/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {

        if (Objects.isNull(id)) {
            return badRequest("Request body is required!");
        }

        LOGGER.info("Start querying sql!");
        return response(service.deleteReport(id));
    }

    @PostMapping("/reports/view")
    public ResponseEntity<Object> generatePdf(@RequestBody ViewReportDto dto) {

        if (Objects.isNull(dto)) {
            return badRequest("Request body is required!");
        }

        LOGGER.info("Start querying sql!");

        ByteArrayInputStream bis = (ByteArrayInputStream) resourceService.generatePdfFile(dto).getResult();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=customers.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
