package com.staticor.repositories;

import com.staticor.models.reports.ReportChartColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportChartColumnRepository extends JpaRepository<ReportChartColumn, Long> {
}
