package com.pim.develize.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectGetShortResponse {

    private Long project_id;

    private String projectName;

    private String projectType;

    private String projectStatus;

}
