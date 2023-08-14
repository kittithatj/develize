package com.pim.develize.api;

import com.pim.develize.entity.JobAssessment;
import com.pim.develize.entity.Personnel;
import com.pim.develize.exception.BaseException;
import com.pim.develize.model.AssessmentModel;
import com.pim.develize.model.PersonnelModel;
import com.pim.develize.model.SetSkillModel;
import com.pim.develize.service.JobAssessmentService;
import com.pim.develize.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<JobAssessment> assessPersonnel(@RequestBody AssessmentModel a){
        JobAssessment response = jobAssessmentService.assessPersonnel(a);
        return ResponseEntity.ok(response);
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
