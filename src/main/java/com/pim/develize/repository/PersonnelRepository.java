package com.pim.develize.repository;

import com.pim.develize.entity.Personnel;
import com.pim.develize.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PersonnelRepository extends CrudRepository<Personnel, Long> {

    public List<Personnel> findAll();

    public List<Personnel> findAllByOrderByLastUpdateDesc();

    public Optional<Personnel> findByFirstName(String firstName);

    @Query(value="SELECT p FROM Personnel p where p.firstName like %:name% or p.lastName like %:name%")
    public Optional<Personnel> findByNameLike(String name);

    @Query(value = "SELECT DISTINCT p.division FROM Personnel p")
    public List<String> findDivisionList();

    @Query(value = "SELECT DISTINCT p.position FROM Personnel p")
    public List<String> findPositionList();

    @Query(value = "SELECT project.personnel FROM Personnel personnel, ProjectHistory project WHERE project.project.project_id = :project_id")
    public List<Personnel> findByProjectId(Long project_id);
}
