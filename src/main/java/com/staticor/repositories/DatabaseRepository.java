package com.staticor.repositories;

import com.staticor.models.connections.Database;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseRepository extends JpaRepository<Database, Long> {
}
