package com.pim.develize.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillGetResponse {
    public Long skill_id;
    public String skillName;
    public String skillType;
}
