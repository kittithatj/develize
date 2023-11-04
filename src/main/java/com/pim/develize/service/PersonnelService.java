package com.pim.develize.service;

import com.pim.develize.entity.Personnel;
import com.pim.develize.entity.Project;
import com.pim.develize.entity.Skill;
import com.pim.develize.exception.BaseException;
import com.pim.develize.exception.PersonnelException;
import com.pim.develize.model.request.PersonnelModel;
import com.pim.develize.model.response.PersonnnelGetResponse;
import com.pim.develize.model.response.ProjectGetShortResponse;
import com.pim.develize.model.response.SkillGetResponse;
import com.pim.develize.repository.JobAssessmentRepository;
import com.pim.develize.repository.PersonnelRepository;
import com.pim.develize.repository.ProjectRepository;
import com.pim.develize.repository.SkillRepository;
import com.pim.develize.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class PersonnelService {

    final
    SkillRepository skillRepository;
    final
    PersonnelRepository personnelRepository;

    final
    ProjectRepository projectRepository;

    final JobAssessmentRepository jobAssessmentRepository;
    public PersonnelService(PersonnelRepository personnelRepository, SkillRepository skillRepository, JobAssessmentRepository jobAssessmentRepository, ProjectRepository projectRepository) {
        this.personnelRepository = personnelRepository;
        this.skillRepository = skillRepository;
        this.jobAssessmentRepository = jobAssessmentRepository;
        this.projectRepository = projectRepository;
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

        List<Skill> skills = new ArrayList<>();
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

    public Personnel editPersonnel(PersonnelModel p) {

//        Personnel personnel = personnelRepository.findById(p.personnel_id).get();
//        personnel.;

        Personnel entity = personnelRepository.findById(p.personnel_id).get();
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

//        entity.setSkills(skills);
        entity.getSkills().clear();
        entity.getSkills().addAll(skills);

        return personnelRepository.save(entity);
    }

    public Personnel setPersonnelSkill(Long personnel_id ,List<Long> skill_id) throws BaseException {
        Optional<Personnel> personnel = personnelRepository.findById(personnel_id);
        if(!personnel.isPresent()){
            throw PersonnelException.setSkillFailed();
        }
        List<Skill> skillSet = new ArrayList<>();
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

    public List<PersonnnelGetResponse> getAllPersonnel(){
        List<Personnel> personnelList = personnelRepository.findAll();
        List<PersonnnelGetResponse> personnelGetList = ObjectMapperUtils.mapAll(personnelList, PersonnnelGetResponse.class);
        personnelGetList.forEach(p -> {
            List<Skill> skills = skillRepository.findAllByPersonnelsPersonnel_id(p.getPersonnel_id());
            List<SkillGetResponse> skillGet = ObjectMapperUtils.mapAll(skills, SkillGetResponse.class);
            List<Project> projects = projectRepository.findAllByPersonnelId(p.getPersonnel_id());
            List<ProjectGetShortResponse> projectGet = ObjectMapperUtils.mapAll(projects, ProjectGetShortResponse.class);
            p.setSkills(skillGet);
            p.setProjectHistories(projectGet);
        });
        return personnelGetList;
    }

    public PersonnnelGetResponse getPersonnelById(Long id) throws PersonnelException {
        Optional<Personnel> opt = personnelRepository.findById(id);
        if(!opt.isPresent()) {
            throw PersonnelException.getInfomationFail();
        }
        PersonnnelGetResponse personnelGet = ObjectMapperUtils.map(opt.get(), PersonnnelGetResponse.class);
        List<Skill> skills = skillRepository.findAllByPersonnelsPersonnel_id(personnelGet.getPersonnel_id());
        List<SkillGetResponse> skillGet = ObjectMapperUtils.mapAll(skills, SkillGetResponse.class);
        List<Project> projects = projectRepository.findAllByPersonnelId(personnelGet.getPersonnel_id());
        List<ProjectGetShortResponse> projectGet = ObjectMapperUtils.mapAll(projects, ProjectGetShortResponse.class);
        personnelGet.setSkills(skillGet);
        personnelGet.setProjectHistories(projectGet);

        return personnelGet;
    }

    public Optional<Personnel> getPersonnelAccessById(Long id) { return personnelRepository.findById(id);}

    public Optional<Personnel> getPersonnelByName(String name){
        return personnelRepository.findByNameLike(name);
    }


}
