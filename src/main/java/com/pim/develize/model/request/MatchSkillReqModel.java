package com.pim.develize.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchSkillReqModel {
    private Long[] skillIdList;
    private Long memberCount;
    private Boolean ignorePS;
}
