package com.staticor.services;

import com.staticor.models.metrics.Chart;
import com.staticor.repositories.ChartRepository;
import org.springframework.stereotype.Service;

@Service
public class ChartService extends ServiceResponse {

    private final ChartRepository repository;

    public ChartService(ChartRepository repository) {
        this.repository = repository;
    }

    public ServiceResponse createChart(Chart chart) {
        try {
            repository.save(chart);
            return success(true).code(200).message("Data Saved!");
        } catch (Exception e) {
            return success(false).code(500).errors(e.getLocalizedMessage());
        }
    }
}