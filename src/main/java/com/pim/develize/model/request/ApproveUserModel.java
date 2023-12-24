package com.pim.develize.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApproveUserModel {
    private Long userId;
    private String role;
}
