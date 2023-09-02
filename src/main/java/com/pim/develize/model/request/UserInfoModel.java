package com.pim.develize.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoModel {
    public Long user_id;
    public String username;
    public String firstName;
    public String lastName;
    public String role;
}
