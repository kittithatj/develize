package com.pim.develize.model.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class PersonnnelGetResponse {

    private Long personnel_id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String division;

    private String position;

    private String employmentStatus;

    private List<SkillGetResponse> skills;

    private List<ProjectGetShortResponse> projectHistories;

    private Timestamp lastUpdate;

}
