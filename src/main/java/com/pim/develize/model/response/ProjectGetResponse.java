package com.pim.develize.model.response;

import com.pim.develize.model.request.PersonnelModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class ProjectGetResponse {

    private Long project_id;

    private String projectName;

    private String projectType;

    private String projectDescription;

    private Date startDate;

    private Date endDate;

    private List<SkillGetResponse> skillsRequired;

    private List<PersonnelModel> projectMember;

    private BigDecimal budget;

    private String projectStatus;

}
