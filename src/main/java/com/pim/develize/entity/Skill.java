package com.pim.develize.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long skill_id;

    @Column(name = "skill_name", nullable = false)
    private String skillName;

    @Column(name = "skill_type", nullable = false)
    private String skillType;

    @ManyToMany(mappedBy = "skills")
    @JsonIgnoreProperties("skills")
    private Set<Personnel> personnels = new HashSet<>();

    @ManyToMany(mappedBy = "skillsRequired")
    @JsonIgnoreProperties("skillsRequired")
    private Set<Project> projects = new HashSet<>();

//    public void removePersonels(Personnel p){
//        this.personnels.remove(p);
//        p.getSkills().remove(this);
//    }
//
//    public void removeProjects(Project p){
//        this.projects.remove(p);
//        p.getSkillsRequired().remove(this);
//    }

    public void removeConstrains(Skill skill){
        for(Personnel p : skill.getPersonnels()){
            this.personnels.remove(p);
            p.getSkills().remove(this);
        }
        for(Project p : skill.getProjects()){
            this.projects.remove(p);
            p.getSkillsRequired().remove(this);
        }
    }

}