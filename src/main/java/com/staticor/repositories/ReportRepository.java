package com.staticor.repositories;

import com.staticor.models.collections.Collection;
import com.staticor.models.reports.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> getAllByCollection(Collection collection);
}
