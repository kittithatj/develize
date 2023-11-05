package com.pim.develize.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssessmentGetResponse {
    public AssessmentGetResponse(){
        deliverableQuality = 0.0;
        teamwork = 0.0;
        innovation = 0.0;
        attitude = 0.0;
        jobKnowledge = 0.0;
        attendance = 0.0;
        jobPerformance = 0.0;
    }
    public Double deliverableQuality;

    public Double teamwork;

    public Double innovation;

    public Double attitude;

    public Double jobKnowledge;

    public Double attendance;

    public Double jobPerformance;

}
