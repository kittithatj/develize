package com.pim.develize.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long user_id;

    @Column(name="username", length=20, nullable=false, unique=true)
    private String username;

    @Column(name="password", nullable=false)
    private String password;

    @Column(name="role", nullable=false)
    private String role;

    @OneToMany(mappedBy = "assessBy", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "assessments")
    private List<JobAssessment> assessments;
}
