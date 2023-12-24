package com.pim.develize.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {
    public String username;

    public String firstName;

    public String lastName;

    public String password;

    public String email;

    public String role;
}
