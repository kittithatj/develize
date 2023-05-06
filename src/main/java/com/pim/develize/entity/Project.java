package com.pim.develize.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Set;

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

    @ManyToMany()
    @JoinTable(
            name = "skills_required",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @JsonIgnoreProperties("projects")
    private Set<Skill> skillsRequired;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "project_assignments")
    private List<ProjectHistory> projectAssignments;

    @Column(name = "budget")
    private BigDecimal budget;

    @Column(name = "project_status")
    private String projectStatus;
}
