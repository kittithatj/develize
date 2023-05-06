package com.pim.develize.repository;

import com.pim.develize.entity.JobAssessment;
import com.pim.develize.entity.Personnel;
import com.pim.develize.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobAssessmentRepository extends CrudRepository<JobAssessment, Long> {

    public List<JobAssessment> findByPersonnel(Personnel personnel);

    public List<JobAssessment> findByAssessBy(User user);

}
