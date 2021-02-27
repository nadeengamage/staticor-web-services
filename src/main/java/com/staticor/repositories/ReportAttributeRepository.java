package com.staticor.repositories;

import com.staticor.models.reports.ReportAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportAttributeRepository extends JpaRepository<ReportAttribute, Long> {
}
