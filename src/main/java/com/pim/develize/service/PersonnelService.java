package com.pim.develize.service;

import com.pim.develize.entity.Personnel;
import com.pim.develize.entity.Skill;
import com.pim.develize.exception.BaseException;
import com.pim.develize.exception.PersonnelException;
import com.pim.develize.model.request.PersonnelModel;
import com.pim.develize.repository.JobAssessmentRepository;
import com.pim.develize.repository.PersonnelRepository;
import com.pim.develize.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class PersonnelService {

    final
    SkillRepository skillRepository;
    final
    PersonnelRepository personnelRepository;

    final JobAssessmentRepository jobAssessmentRepository;
    public PersonnelService(PersonnelRepository personnelRepository, SkillRepository skillRepository, JobAssessmentRepository jobAssessmentRepository) {
        this.personnelRepository = personnelRepository;
        this.skillRepository = skillRepository;
        this.jobAssessmentRepository = jobAssessmentRepository;
    }


    public Personnel createPersonnel(PersonnelModel p) {
        Personnel entity = new Personnel();
        entity.setFirstName(p.firstName);
        entity.setLastName(p.lastName);
        entity.setEmail(p.email);
        entity.setPhoneNumber(p.phoneNumber);
        entity.setDivision(p.division);
        entity.setPosition(p.position);
        entity.setEmploymentStatus(p.employmentStatus);
        entity.setLastUpdate(new Timestamp(System.currentTimeMillis()));

        Set<Skill> skills = new HashSet();
        if(p.skillsId.length > 0){
            for (Long id : p.skillsId)
            {
                Optional<Skill> s = skillRepository.findById(id);
                skills.add(s.get());
            }
        }

        entity.setSkills(skills);

        return personnelRepository.save(entity);
    }

    public Personnel setPersonnelSkill(Long personnel_id ,List<Long> skill_id) throws BaseException {
        Optional<Personnel> personnel = personnelRepository.findById(personnel_id);
        if(!personnel.isPresent()){
            throw PersonnelException.setSkillFailed();
        }
        Set<Skill> skillSet = new HashSet<Skill>();
        for (Long i:skill_id) {
            Optional<Skill> skill = skillRepository.findById(i);
            skillSet.add(skill.get());
        }
        personnel.get().setSkills(skillSet);
        return personnelRepository.save(personnel.get());
    }

    public List<String> getDivisionList() {
        return personnelRepository.findDivisionList();
    }

    public List<String> getPositionList() {
        return personnelRepository.findPositionList();
    }

    public Iterable<Personnel> getAllPersonnel(){
        return personnelRepository.findAll();
    }

    public Optional<Personnel> getPersonnelById(Long id) { return personnelRepository.findById(id);}

    public Optional<Personnel> getPersonnelByName(String name){
        return personnelRepository.findByNameLike(name);
    }


}
