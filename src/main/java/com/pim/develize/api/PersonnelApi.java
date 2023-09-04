package com.pim.develize.api;

import com.pim.develize.entity.JobAssessment;
import com.pim.develize.entity.Personnel;
import com.pim.develize.exception.BaseException;
import com.pim.develize.exception.PersonnelException;
import com.pim.develize.model.request.AssessmentModel;
import com.pim.develize.model.request.PersonnelModel;
import com.pim.develize.model.request.SetSkillModel;
import com.pim.develize.model.response.AssessmentGetResponse;
import com.pim.develize.service.JobAssessmentService;
import com.pim.develize.service.PersonnelService;
import com.pim.develize.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/personnel")
public class PersonnelApi {

    @Autowired
    PersonnelService personnelService;
    @Autowired
    JobAssessmentService jobAssessmentService;

    @GetMapping("/get")
    public ResponseEntity<Iterable<Personnel>> getAllPersonnel(){
        Iterable<Personnel> response = personnelService.getAllPersonnel();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personnel> getPersonnelById(@PathVariable("id") Long id){
        Personnel response = personnelService.getPersonnelById(id).get();
        return ResponseEntity.ok(response);
    }

    @GetMapping("get-access/{id}")
    public ResponseEntity<AssessmentGetResponse> getPersonnelAssessById(@PathVariable("id") Long id) throws BaseException {
        Optional<JobAssessment> opt = jobAssessmentService.getPersonnelAssessment(id);
        JobAssessment jobAssessment;
        if(opt.isPresent()){
            jobAssessment = opt.get();
        }else{
            throw PersonnelException.assessNotFound();
        }
        return ResponseEntity.ok(ObjectMapperUtils.map(jobAssessment,AssessmentGetResponse.class));
    }

    @PostMapping("/create")
    public ResponseEntity<Personnel> createPersonnel(@RequestBody PersonnelModel personnel){
        Personnel response = personnelService.createPersonnel(personnel);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/skill/set")
    public ResponseEntity<Personnel> setPersonnelSkill(@RequestBody SetSkillModel s) throws BaseException {
        Personnel response = personnelService.setPersonnelSkill(s.personnel_id,s.skill_id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/assess")
    public ResponseEntity<AssessmentGetResponse> assessPersonnel(@RequestBody AssessmentModel a){
        JobAssessment response = jobAssessmentService.assessPersonnel(a);
        return ResponseEntity.ok(ObjectMapperUtils.map(response,AssessmentGetResponse.class));
    }

    @GetMapping("/division/list")
    public ResponseEntity<List<String>> getDivisionList(){
        return ResponseEntity.ok(personnelService.getDivisionList());
    }

    @GetMapping("/position/list")
    public ResponseEntity<List<String>> getPositionList(){
        return ResponseEntity.ok(personnelService.getPositionList());
    }
}
