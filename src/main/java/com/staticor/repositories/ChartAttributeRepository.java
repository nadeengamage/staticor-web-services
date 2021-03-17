package com.staticor.repositories;

import com.staticor.models.metrics.ChartAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartAttributeRepository extends JpaRepository<ChartAttribute, Long> {
}
