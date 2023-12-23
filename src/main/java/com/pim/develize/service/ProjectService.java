package com.pim.develize.service;

import com.pim.develize.entity.*;
import com.pim.develize.exception.BaseException;
import com.pim.develize.exception.PersonnelException;
import com.pim.develize.exception.ProjectException;
import com.pim.develize.model.MailModel;
import com.pim.develize.model.request.PersonnelAssignHistory;
import com.pim.develize.model.request.PersonnelModel;
import com.pim.develize.model.request.ProjectCreateModel;
import com.pim.develize.model.response.ProjectGetEditResponse;
import com.pim.develize.model.response.ProjectGetResponse;
import com.pim.develize.repository.PersonnelRepository;
import com.pim.develize.repository.ProjectHistoryRepository;
import com.pim.develize.repository.ProjectRepository;
import com.pim.develize.repository.SkillRepository;
import com.pim.develize.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormatSymbols;
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

    final
    MailService mailService;

    public ProjectService(ProjectRepository projectRepository, PersonnelRepository personnelRepository, SkillRepository skillRepository, ProjectHistoryRepository projectHistoryRepository, MailService mailService) {
        this.projectRepository = projectRepository;
        this.personnelRepository = personnelRepository;
        this.skillRepository = skillRepository;
        this.projectHistoryRepository = projectHistoryRepository;
        this.mailService = mailService;
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

    public ProjectGetEditResponse GetProjectById(Long id) throws BaseException {
        Optional<Project> opt = projectRepository.findById(id);
        if(opt.isEmpty()){
            throw ProjectException.ProjectNotFound();
        }
        Project project = opt.get();
        ProjectGetEditResponse projectEditRes = ObjectMapperUtils.map(project, ProjectGetEditResponse.class);
        List<String> roleList = new ArrayList<>();
        List<Personnel> personnelList = personnelRepository.findByProjectId(projectEditRes.getProject_id());
        List<PersonnelModel> personnels = ObjectMapperUtils.mapAll(personnelList, PersonnelModel.class);
        personnelList.forEach(personnel -> {
                ProjectHistory h = projectHistoryRepository.findByPersonnelAndProject(personnel, project);
                roleList.add(h.getRole());
        });
        for (int i = 0; i < roleList.size(); i++) {
            personnels.get(i).setRole(roleList.get(i));
        }
        projectEditRes.setProjectMember(personnels);

        return projectEditRes;
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
        if(params.getEndDate() != null){
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

    @Transactional
    public ProjectGetResponse editProject(ProjectCreateModel params) throws BaseException, ParseException {
        Optional<Project> pOpt = projectRepository.findById(params.getProject_id());
        if(pOpt.isEmpty()){
            throw ProjectException.ProjectNotFound();
        }

        Project project = pOpt.get();

        if(params.getStartDate() != null){
            Date startDate;
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            startDate = format.parse(params.getStartDate());
            project.setStartDate(startDate);
        }
        if(params.getStartDate() != null){
            Date endDate;
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            endDate = format.parse(params.getEndDate());
            project.setEndDate(endDate);
        }

        project.setProjectName(params.getProjectName());
        project.setProjectDescription(params.getProjectDescription());
        project.setProjectType(params.getProjectType());
        project.setProjectStatus(params.getProjectStatus());
        project.setBudget(params.getBudget());

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

        List<ProjectHistory> memberAssign = new ArrayList<>();
        assignmentList.forEach(a -> {
            ProjectHistory history = new ProjectHistory();
            history.setProject(project);
            history.setPersonnel(a.getPersonnel());
            history.setRole(a.getRole());
            history.setAssignDate(new Date());
            ProjectHistory history_ = projectHistoryRepository.save(history);
            memberAssign.add(history_);
        });

        //projectHistoryRepository.deleteAllByProject(project);

        project.setSkillsRequired(skillList);
        project.setProjectAssignmentsEdit(memberAssign);

        Project savedProject = projectRepository.save(project);
        ProjectGetResponse response = ObjectMapperUtils.map(savedProject, ProjectGetResponse.class);
        List<Personnel> personnelList_ = personnelRepository.findByProjectId(savedProject.getProject_id());
        List<PersonnelModel> personnels = ObjectMapperUtils.mapAll(personnelList_, PersonnelModel.class);
        response.setProjectMember(personnels);

        return response;
    }

    public void deleteProjectById(Long id) throws BaseException {
        Optional<Project> opt = projectRepository.findById(id);
        if(opt.isPresent()) {

            opt.get().removeConstrains(opt.get());

            List<ProjectHistory> hList = projectHistoryRepository.findAllByProject(opt.get());
            for(ProjectHistory h : hList){
                projectHistoryRepository.delete(h);
            }

            projectRepository.delete(opt.get());

        }else{
            throw ProjectException.ProjectNotFound();
        }

    }

    public void sendProjectAssignMail(Project p, String mailAddress){

        String[] months = new String[]{
                "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        };

        p.getStartDate().getMonth();

        String startDate = p.getStartDate().getDate()+" "+months[p.getStartDate().getMonth()-1]+" "+p.getStartDate().getYear();
        String endDate = p.getEndDate().getDate()+" "+months[p.getEndDate().getMonth()-1]+" "+p.getEndDate().getYear();

        MailModel mail = new MailModel();
        mail.setSubject("You have been assigned to Develize project! : "+p.getProjectName());
        mail.setMessage("<h5>We would like to inform you that you have been assigned to project with the information below</h5><br> <br>" +
                "Project Name : " +p.getProjectName() + "<br>"+
                "Project Type : " +p.getProjectType() +"<br>"+
                "Description : " +p.getProjectDescription() +"<br>"+
                "" +
                "" +
                "This is an automatically generated email – please do not reply to it. If you have any");


        mailService.sendEmail(mailAddress,mail);
    }
}
