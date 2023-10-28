package com.pim.develize.service;

import com.pim.develize.entity.Personnel;
import com.pim.develize.entity.Project;
import com.pim.develize.entity.ProjectHistory;
import com.pim.develize.entity.Skill;
import com.pim.develize.exception.BaseException;
import com.pim.develize.model.request.PersonnelAssignHistory;
import com.pim.develize.model.request.PersonnelModel;
import com.pim.develize.model.request.ProjectCreateModel;
import com.pim.develize.model.response.ProjectGetResponse;
import com.pim.develize.repository.PersonnelRepository;
import com.pim.develize.repository.ProjectHistoryRepository;
import com.pim.develize.repository.ProjectRepository;
import com.pim.develize.repository.SkillRepository;
import com.pim.develize.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProjectService {

    final
    ProjectRepository projectRepository;
    final
    PersonnelRepository personnelRepository;
    final
    SkillRepository skillRepository;
    final
    ProjectHistoryRepository projectHistoryRepository;

    public ProjectService(ProjectRepository projectRepository, PersonnelRepository personnelRepository, SkillRepository skillRepository, ProjectHistoryRepository projectHistoryRepository) {
        this.projectRepository = projectRepository;
        this.personnelRepository = personnelRepository;
        this.skillRepository = skillRepository;
        this.projectHistoryRepository = projectHistoryRepository;
    }

    public List<ProjectGetResponse> GetProjectList() {
        List<Project> projects =  projectRepository.findAll();
        List<ProjectGetResponse> projectResponse = ObjectMapperUtils.mapAll(projects, ProjectGetResponse.class);
        projectResponse.forEach(p -> {
            List<Personnel> personnelList = personnelRepository.findByProjectId(p.getProject_id());
            List<PersonnelModel> personnels = ObjectMapperUtils.mapAll(personnelList, PersonnelModel.class);
            p.setProjectMember(personnels);
        });
        return projectResponse;
    }

    public ProjectGetResponse CreateProject(ProjectCreateModel params) throws ParseException {
        Project project = new Project();
        Date startDate = new Date();
        Date endDate =  new Date();
        if(params.getStartDate() != null){
            params.getStartDate();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            startDate = format.parse(params.getStartDate());
        }
        if(params.getStartDate() != null){
            params.getEndDate();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            endDate = format.parse(params.getEndDate());
        }
        project.setProjectName(params.getProjectName());
        project.setProjectDescription(params.getProjectDescription());
        project.setProjectStatus(params.getProjectStatus());
        project.setProjectType(params.getProjectType());
        project.setBudget(params.getBudget());
        project.setStartDate(startDate);
        project.setEndDate(endDate);

        List<Skill> skillList = new ArrayList<>();
        params.getSkillRequireIdList().forEach(skillId -> {
            Skill s = skillRepository.findById(skillId).get();
            skillList.add(s);
        });

        List<PersonnelAssignHistory> assignmentList = new ArrayList<>();
        params.getMemberAssignment().forEach(assignment -> {
            Personnel p = personnelRepository.findById(assignment.personnel_id).get();
            PersonnelAssignHistory saveAssign = new PersonnelAssignHistory();
            saveAssign.setPersonnel(p);
            saveAssign.setRole(assignment.role);
            assignmentList.add(saveAssign);

        });

        project.setSkillsRequired(skillList);

        Project newProject = projectRepository.save(project);

        List<ProjectHistory> memberAssign = new ArrayList<>();

        assignmentList.forEach(a -> {
            ProjectHistory history = new ProjectHistory();
            history.setProject(newProject);
            history.setPersonnel(a.getPersonnel());
            history.setRole(a.getRole());
            history.setAssignDate(new Date());
            ProjectHistory history_ = projectHistoryRepository.save(history);
            memberAssign.add(history_);
        });

        newProject.setProjectAssignments(memberAssign);

        Project finalProject = projectRepository.save(newProject);
        ProjectGetResponse response = ObjectMapperUtils.map(finalProject, ProjectGetResponse.class);
        List<Personnel> personnelList_ = personnelRepository.findByProjectId(finalProject.getProject_id());
        List<PersonnelModel> personnels = ObjectMapperUtils.mapAll(personnelList_, PersonnelModel.class);
        response.setProjectMember(personnels);

        return response;
    }
}
