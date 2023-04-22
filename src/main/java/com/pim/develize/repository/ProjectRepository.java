package com.pim.develize.repository;

import com.pim.develize.entity.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    public Optional<Project> findByProjectName(String projectName);
}
