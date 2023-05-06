package com.pim.develize.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnel_id")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User assessBy;

}
