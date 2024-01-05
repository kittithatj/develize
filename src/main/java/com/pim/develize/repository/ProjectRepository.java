package com.pim.develize.repository;

import com.pim.develize.entity.Project;
import com.pim.develize.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    public Optional<Project> findByProjectName(String projectName);

    @Query(value = "SELECT project.project FROM Personnel personnel, ProjectHistory project WHERE project.personnel.personnel_id = :personnel_id")
    public List<Project> findAllByPersonnelId(Long personnel_id);

    public List<Project> findAll();

    public List<Project> findAllByOrderByLastUpdateDesc();
}
