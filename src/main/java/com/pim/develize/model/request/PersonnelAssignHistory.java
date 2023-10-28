package com.pim.develize.model.request;

import com.pim.develize.entity.Personnel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonnelAssignHistory {
    public Personnel personnel;
    public String role;
}
