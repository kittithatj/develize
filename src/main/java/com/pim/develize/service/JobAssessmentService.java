package com.pim.develize.service;

import com.pim.develize.entity.JobAssessment;
import com.pim.develize.entity.Personnel;
import com.pim.develize.entity.User;
import com.pim.develize.exception.BaseException;
import com.pim.develize.exception.PersonnelException;
import com.pim.develize.model.request.AssessmentModel;
import com.pim.develize.repository.JobAssessmentRepository;
import com.pim.develize.repository.PersonnelRepository;
import com.pim.develize.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        User user = userRepository.findById(userId).get();

        Optional<JobAssessment> opt = jobAssessmentRepository.findByAssessByAndPersonnel(user,personnel);
        if(opt.isPresent()){
            assessment.setAssessment_id(opt.get().getAssessment_id());
        }
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

    public Optional<JobAssessment> getPersonnelAssessment(Long personnelId) throws BaseException{
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        Optional<Personnel> optP = personnelRepository.findById(personnelId);
        Optional<User> optU = userRepository.findById(userId);

        Personnel personnel = new Personnel();
        User user = new User();
        if(optP.isPresent() && optU.isPresent()){
            personnel = optP.get();
            user = optU.get();
        }else{
            throw PersonnelException.invlidId();
        }
        return jobAssessmentRepository.findByAssessByAndPersonnel(user,personnel);
    }

}
