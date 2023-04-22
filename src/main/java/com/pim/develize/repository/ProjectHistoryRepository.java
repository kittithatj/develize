package com.pim.develize.repository;

import com.pim.develize.entity.Personnel;
import com.pim.develize.entity.Project;
import com.pim.develize.entity.ProjectHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProjectHistoryRepository extends CrudRepository<ProjectHistory,Long> {

    public Optional<ProjectHistory> findByProject(Project project);

    public Optional<ProjectHistory> findByPersonnel(Personnel personnel);
}
