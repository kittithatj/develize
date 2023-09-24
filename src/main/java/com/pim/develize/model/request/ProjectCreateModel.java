package com.pim.develize.model.request;

import com.pim.develize.model.response.SkillGetResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class ProjectCreateModel {
    private Long project_id;

    private String projectName;

    private String projectType;

    private String projectDescription;

    private String startDate;

    private String endDate;

    private List<Long> skillRequireIdList;

    private List<Long> memberIdList;

    private BigDecimal budget;

    private String projectStatus;

}
