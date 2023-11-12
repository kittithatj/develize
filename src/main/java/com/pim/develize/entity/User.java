package com.pim.develize.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long user_id;

    @Column(name="username", length=20, nullable=false, unique=true)
    private String username;

    @Column(name="first_name", nullable=false)
    private String firstName;

    @Column(name="last_name", nullable=false)
    private String lastName;

    @Column(name="password", nullable=false)
    private String password;

    @Column(name="role", nullable=false)
    private String role;

    @OneToMany(mappedBy = "assessBy", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "assessments")
    @JsonIgnoreProperties(value = "assessBy")
    private List<JobAssessment> assessments;
}
