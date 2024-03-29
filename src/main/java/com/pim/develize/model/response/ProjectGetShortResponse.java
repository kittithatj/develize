package com.pim.develize.model.response;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProjectGetShortResponse {

    private Long project_id;

    private String projectName;

    private String projectType;

    private String projectStatus;

    private Date startDate;

    private Date endDate;

}
