package com.pim.develize.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.*;

@Entity
@Getter
@Setter
public class Personnel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long personnel_id;

    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 30, nullable = false)
    private String lastName;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Column(name = "division")
    private String division;

    @Column(name = "position")
    private String position;

    @Column(name = "employment_status")
    private String employmentStatus;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "personnel_skills",
            joinColumns = @JoinColumn(name = "personnel_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @JsonIgnoreProperties("personnels")
    private List<Skill> skills= new ArrayList<>();

    @OneToMany(mappedBy = "personnel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "project_histories")
    @JsonIgnoreProperties(value = "personnel")
    private List<ProjectHistory> projectHistories;

    @OneToMany(mappedBy = "personnel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "assessments")
    @JsonIgnoreProperties(value = "personnel")
    private List<JobAssessment> assessments;

    @Column(name = "last_update")
    private Timestamp lastUpdate;
}
