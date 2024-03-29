package com.pim.develize.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long project_id;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "project_type", nullable = false)
    private String projectType;

    @Column(name = "project_description")
    private String projectDescription;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "skills_required",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @JsonIgnoreProperties("projects")
    private List<Skill> skillsRequired = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "project_assignments")
    @JsonIgnoreProperties(value = "project")
    private List<ProjectHistory> projectAssignments;

    @Column(name = "budget")
    private BigDecimal budget;

    @Column(name = "project_status")
    private String projectStatus;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public void removeConstrains(Project project){
        this.skillsRequired.removeAll(project.getSkillsRequired());
        for(Skill s : project.getSkillsRequired()){
            s.getProjects().remove(this);
        }
    }

    public void setProjectAssignmentsEdit(List<ProjectHistory>  pList){
        if(this.projectAssignments != null){
            this.projectAssignments.clear();
        }
        if(pList != null){
            this.projectAssignments.addAll(pList);
        }
    }

}
