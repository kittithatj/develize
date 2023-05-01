package com.pim.develize.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @ManyToMany()
    @JoinTable(
            name = "personnel_skills",
            joinColumns = @JoinColumn(name = "personnel_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills;

    @OneToMany(mappedBy = "personnel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "project_histories")
    private List<ProjectHistory> projectHistories;

    @Column(name = "last_update")
    private Timestamp lastUpdate;
}
