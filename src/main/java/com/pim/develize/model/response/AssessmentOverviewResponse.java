package com.pim.develize.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssessmentOverviewResponse {
    public Long personnel_id;
    public String fullName;
    public AssessmentGetResponse overviewScore;
    public AssessmentGetResponse userScore;
}
