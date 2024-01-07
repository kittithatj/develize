package com.pim.develize.api;

import com.pim.develize.exception.BaseException;
import com.pim.develize.model.request.MatchSkillReqModel;
import com.pim.develize.model.request.ProjectCreateModel;
import com.pim.develize.model.response.PersonnnelGetResponse;
import com.pim.develize.model.response.ProjectGetEditResponse;
import com.pim.develize.model.response.ProjectGetResponse;
import com.pim.develize.service.ProjectService;
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
    public ResponseEntity<List<ProjectGetResponse>> GetProjectList() throws BaseException {
        List<ProjectGetResponse> projectList = projectService.GetProjectList();
        return ResponseEntity.ok(projectList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectGetEditResponse> GetProjectById(@PathVariable("id") Long id) throws BaseException {
        ProjectGetEditResponse p = projectService.GetProjectById(id);
        return ResponseEntity.ok(p);
    }

    @PostMapping("/create")
    public ResponseEntity<ProjectGetResponse> CreateProject(@RequestBody ProjectCreateModel params) throws ParseException {
        ProjectGetResponse project = projectService.CreateProject(params);
        return ResponseEntity.ok(project);
    }

    @PutMapping("/edit")
    public ResponseEntity<ProjectGetResponse> EditProject(@RequestBody ProjectCreateModel params) throws BaseException, ParseException {
        ProjectGetResponse project = projectService.editProject(params);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> DeleteProject(@PathVariable("id") Long id) throws BaseException {
        projectService.deleteProjectById(id);
        return ResponseEntity.ok("Delete Project Successfully");
    }

    @GetMapping("/{id}/match-skill/{count}/{ignorePS}")
    public ResponseEntity<List<PersonnnelGetResponse>> matchRequiredSkills(@PathVariable("id") Long id, @PathVariable("count") Long count, @PathVariable("ignorePS") Boolean ignorePS) throws BaseException {
        List<PersonnnelGetResponse> res = projectService.matchRequiredSkills(id, count, ignorePS);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/match-skill")
    public ResponseEntity<List<PersonnnelGetResponse>> matchRequiredSkills(@RequestBody MatchSkillReqModel params) throws BaseException {
        List<PersonnnelGetResponse> res = projectService.matchRequiredSkillsNew(params.getSkillIdList(), params.getMemberCount(), params.getIgnorePS());
        return ResponseEntity.ok(res);
    }

}
