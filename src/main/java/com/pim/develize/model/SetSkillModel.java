package com.pim.develize.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetSkillModel {
    public Long set_id;
    public List<Long> skill_id;
}
