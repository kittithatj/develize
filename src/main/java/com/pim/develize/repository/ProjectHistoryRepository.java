package com.pim.develize.repository;

import com.pim.develize.entity.ProjectHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProjectHistoryRepository extends CrudRepository<ProjectHistory,Long> {

    public Optional<ProjectHistory> findByProject_id(Long id);

    public Optional<ProjectHistory> findByPersonnel_id(Long id);
}
