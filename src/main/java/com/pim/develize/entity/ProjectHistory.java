package com.pim.develize.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnel_id")
    private Personnel personnel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    private String role;

    @Column(name = "assign_date")
    private Date assignDate;

}
