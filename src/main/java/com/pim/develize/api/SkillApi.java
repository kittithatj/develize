package com.pim.develize.api;

import com.pim.develize.entity.Skill;
import com.pim.develize.exception.BaseException;
import com.pim.develize.exception.SkillException;
import com.pim.develize.model.request.SkillModel;
import com.pim.develize.model.response.SkillGetResponse;
import com.pim.develize.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/skill")
public class SkillApi {

    @Autowired
    SkillService skillService;

    @PostMapping("/create")
    public ResponseEntity<Skill> createSkill(@RequestBody SkillModel s) throws BaseException {
        Skill skill = skillService.createSkill(s);
        if (s == null) {
            throw SkillException.NullRequestPost();
        } else {
            return new ResponseEntity<>(skill, HttpStatus.CREATED);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<SkillGetResponse>> getAllSkills() {
        List<Skill> skills = skillService.getAllSKills();
        List<SkillGetResponse> response = new ArrayList<>();

        for (Skill s : skills) {
            SkillGetResponse r = new SkillGetResponse();
            r.setSkill_id(s.getSkill_id());
            r.setSkillName(s.getSkillName());
            r.setSkillType(s.getSkillType());
            response.add(r);
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSkill(@PathVariable("id") Long id) throws BaseException{
        skillService.deleteSkillById(id);
        return ResponseEntity.ok("Delete Skill id : "+id);
    }
}
