package com.pim.develize.service;

import com.pim.develize.entity.JobAssessment;
import com.pim.develize.entity.Personnel;
import com.pim.develize.entity.User;
import com.pim.develize.model.AssessmentModel;
import com.pim.develize.repository.JobAssessmentRepository;
import com.pim.develize.repository.PersonnelRepository;
import com.pim.develize.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobAssessmentService {

    final
    JobAssessmentRepository jobAssessmentRepository;

    final
    PersonnelRepository personnelRepository;

    final
    UserRepository userRepository;

    public JobAssessmentService(JobAssessmentRepository jobAssessmentRepository, PersonnelRepository personnelRepository, UserRepository userRepository) {
        this.jobAssessmentRepository = jobAssessmentRepository;
        this.personnelRepository = personnelRepository;
        this.userRepository = userRepository;
    }

    public JobAssessment assessPersonnel(AssessmentModel m){
        JobAssessment assessment = new JobAssessment();
        Personnel personnel = personnelRepository.findById(m.personnel_id).get();
        User user = userRepository.findById(m.user_id).get();
        assessment.setPersonnel(personnel);
        assessment.setAssessBy(user);
        assessment.setDeliverableQuality(m.deliverableQuality);
        assessment.setAttitude(m.attitude);
        assessment.setAttendance(m.attendance);
        assessment.setJobKnowledge(m.jobKnowledge);
        assessment.setInnovation(m.innovation);
        assessment.setTeamwork(m.teamwork);
        assessment.setJobPerformance(Double.valueOf(m.deliverableQuality+m.jobKnowledge+m.attendance+m.attitude+m.innovation+m.teamwork)/6);

        return jobAssessmentRepository.save(assessment);
    }

}
