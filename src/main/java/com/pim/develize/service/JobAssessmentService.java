package com.pim.develize.service;

import com.pim.develize.entity.JobAssessment;
import com.pim.develize.entity.Personnel;
import com.pim.develize.entity.User;
import com.pim.develize.exception.BaseException;
import com.pim.develize.exception.PersonnelException;
import com.pim.develize.model.request.AssessmentModel;
import com.pim.develize.model.response.AssessmentGetResponse;
import com.pim.develize.model.response.AssessmentOverviewResponse;
import com.pim.develize.repository.JobAssessmentRepository;
import com.pim.develize.repository.PersonnelRepository;
import com.pim.develize.repository.UserRepository;
import com.pim.develize.util.ObjectMapperUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public AssessmentOverviewResponse getPersonnelOverviewAssessment(Long personnelId) throws BaseException{
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

        Optional<JobAssessment> userScore = jobAssessmentRepository.findByAssessByAndPersonnel(user,personnel);
        List<JobAssessment> scoreList = jobAssessmentRepository.findAllByPersonnel(personnel);

        AssessmentGetResponse overviewScore = new AssessmentGetResponse();
        if(scoreList.size()>0){
            scoreList.forEach(j -> {
                overviewScore.attendance += j.getAttendance();
                overviewScore.jobPerformance += j.getJobPerformance();
                overviewScore.jobKnowledge += j.getJobKnowledge();
                overviewScore.attitude += j.getAttitude();
                overviewScore.deliverableQuality += j.getDeliverableQuality();
                overviewScore.innovation += j.getInnovation();
                overviewScore.teamwork += j.getTeamwork();
            });

            overviewScore.attendance = overviewScore.attendance/scoreList.size();
            overviewScore.jobPerformance = overviewScore.jobPerformance/scoreList.size();
            overviewScore.jobKnowledge = overviewScore.jobKnowledge/scoreList.size();
            overviewScore.attitude = overviewScore.attitude/scoreList.size();
            overviewScore.deliverableQuality = overviewScore.deliverableQuality/scoreList.size();
            overviewScore.innovation = overviewScore.innovation/scoreList.size();
            overviewScore.teamwork = overviewScore.teamwork/scoreList.size();
        }

        if(userScore.isEmpty() && (scoreList.size() == 0)){
            throw PersonnelException.assessNotFound();
        }
        AssessmentGetResponse userScoreRes = null;
        if(userScore.isPresent()){
             userScoreRes = ObjectMapperUtils.map(userScore.get(),AssessmentGetResponse.class);
        }
        AssessmentOverviewResponse res = new AssessmentOverviewResponse();
        res.setUserScore(userScoreRes);
        res.setOverviewScore(overviewScore);

        return res;
    }

}
