package com.staticor.repositories;

import com.staticor.models.reports.ReportChart;
import com.staticor.models.reports.ReportChartColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportChartColumnRepository extends JpaRepository<ReportChartColumn, Long> {

    Optional<ReportChartColumn> getByReportChartAndColumnAxis(ReportChart reportChart, String axis);

    Optional<List<ReportChartColumn>> getAllByReportChartAndColumnAxisOrColumnAxis(ReportChart reportChart, String axis1, String axis2);
}
