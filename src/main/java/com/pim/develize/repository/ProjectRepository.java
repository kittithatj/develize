package com.pim.develize.repository;

import com.pim.develize.entity.Project;
import com.pim.develize.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    public Optional<Project> findByProjectName(String projectName);

    public List<Project> findAll();
}
