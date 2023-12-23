package com.pim.develize.repository;

import com.pim.develize.entity.Personnel;
import com.pim.develize.entity.Project;
import com.pim.develize.entity.ProjectHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectHistoryRepository extends CrudRepository<ProjectHistory,Long> {

    public Optional<ProjectHistory> findByProject(Project project);

    public List<ProjectHistory> findAllByPersonnel(Personnel personnel);

//    @Query(value="SELECT h FROM ProjectHistory WHERE h.personnel.personnel_id = :personnel_id")
//    public ProjectHistory findByPersonnelId(Long personnel_id);

    public ProjectHistory findByPersonnelAndProject(Personnel personnel, Project project);

    public List<ProjectHistory> findAllByProject(Project project);

    public void deleteAllByProject(Project project);
}
