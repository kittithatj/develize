package com.pim.develize.api;

import com.pim.develize.entity.Personnel;
import com.pim.develize.exception.BaseException;
import com.pim.develize.model.PersonnelModel;
import com.pim.develize.model.SetSkillModel;
import com.pim.develize.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personnel")
public class PersonnelApi {

    @Autowired
    PersonnelService personnelService;

    @GetMapping("/get")
    public ResponseEntity<Iterable<Personnel>> getAllPersonnel(){
        Iterable<Personnel> response = personnelService.getAllPersonnel();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Personnel> createPersonnel(@RequestBody PersonnelModel personnel){
        Personnel response = personnelService.createPersonnel(personnel);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/skill/set")
    public ResponseEntity<Personnel> setPersonnelSkill(@RequestBody SetSkillModel s) throws BaseException {
        Personnel response = personnelService.setPersonnelSkill(s.set_id,s.skill_id);
        return ResponseEntity.ok(response);
    }
}
