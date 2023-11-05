package com.pim.develize.repository;

import com.pim.develize.entity.JobAssessment;
import com.pim.develize.entity.Personnel;
import com.pim.develize.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JobAssessmentRepository extends CrudRepository<JobAssessment, Long> {

    public List<JobAssessment> findAllByPersonnel(Personnel personnel);

    public List<JobAssessment> findByAssessBy(User user);

    public Optional<JobAssessment> findByAssessByAndPersonnel(User user, Personnel personnel);

}
