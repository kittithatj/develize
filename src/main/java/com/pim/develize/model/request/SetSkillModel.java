package com.pim.develize.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetSkillModel {
    public Long personnel_id;
    public List<Long> skill_id;
}
