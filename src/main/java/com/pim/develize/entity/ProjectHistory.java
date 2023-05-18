package com.pim.develize.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
public class ProjectHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long history_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personnel_id")
    @JsonIgnoreProperties("projectHistories")
    private Personnel personnel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties("projectAssignments")
    private Project project;

    private String role;

    @Column(name = "assign_date")
    private Date assignDate;

}
