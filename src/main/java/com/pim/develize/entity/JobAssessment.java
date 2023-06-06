package com.pim.develize.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class JobAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long assessment_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personnel_id")
    @JsonIgnoreProperties({"assessments","skills","projectHistories"})

    private Personnel personnel;

    @Column(name = "deliverable_quality", nullable = false)
    private Integer deliverableQuality;
    @Column(name = "teamwork", nullable = false)
    private Integer teamwork;

    @Column(name = "innovation", nullable = false)
    private Integer innovation;

    @Column(name = "attitude", nullable = false)
    private Integer attitude;

    @Column(name = "job_knowledge", nullable = false)
    private Integer jobKnowledge;

    @Column(name = "attendance", nullable = false)
    private Integer attendance;

    @Column(name = "job_performance", nullable = false)
    private Double jobPerformance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("assessments")
    private User assessBy;

}
