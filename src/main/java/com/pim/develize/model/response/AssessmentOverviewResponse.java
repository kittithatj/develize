package com.pim.develize.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssessmentOverviewResponse {
    public AssessmentGetResponse overviewScore;
    public AssessmentGetResponse userScore;

}
