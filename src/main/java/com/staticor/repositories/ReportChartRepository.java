package com.staticor.repositories;

import com.staticor.models.reports.ReportChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportChartRepository extends JpaRepository<ReportChart, Long> {
}
