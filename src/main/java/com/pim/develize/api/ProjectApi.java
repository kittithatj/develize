package com.pim.develize.api;

import com.pim.develize.exception.BaseException;
import com.pim.develize.model.request.ProjectCreateModel;
import com.pim.develize.model.request.SkillModel;
import com.pim.develize.model.response.ProjectGetResponse;
import com.pim.develize.repository.PersonnelRepository;
import com.pim.develize.repository.ProjectRepository;
import com.pim.develize.repository.SkillRepository;
import com.pim.develize.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/project")
public class ProjectApi {

    final
    ProjectService projectService;

    public ProjectApi(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/get-list")
    public ResponseEntity<List<ProjectGetResponse>> GetPersonnelList() throws BaseException {
        List<ProjectGetResponse> projectList = projectService.GetProjectList();
        return ResponseEntity.ok(projectList);
    }

    @PostMapping("/create")
    public ResponseEntity<ProjectGetResponse> CreateProject(@RequestBody ProjectCreateModel params) throws ParseException {
        ProjectGetResponse project = projectService.CreateProject(params);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> DeleteProject(@PathVariable("id") Long id) throws BaseException{
        projectService.deleteProjectById(id);
        return ResponseEntity.ok("Delete Project Successfully");
    }

}
