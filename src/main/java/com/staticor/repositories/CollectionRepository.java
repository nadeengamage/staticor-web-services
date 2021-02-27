package com.staticor.repositories;

import com.staticor.models.collections.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    @Query("SELECT c.id, c.name, c.description FROM Collection c WHERE c.userId = :userId")
    Optional<List<Object>> getAllByUserId(String userId);
}
