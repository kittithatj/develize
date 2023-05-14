package com.pim.develize.model;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonnelModel {

    public String firstName;

    public String lastName;

    public String email;

    public String phoneNumber;

    public String division;

    public String position;

    public String employmentStatus;
}
