package com.pim.develize.exception;

public class PersonnelException extends BaseException{

    public PersonnelException(String code) {
        super("personnel" + code);
    }

    public static PersonnelException CreateFailed() {
        return new PersonnelException(".create.failed");
    }

    public static PersonnelException personnelNotFound() { return new PersonnelException("not.found");}

    public static PersonnelException setSkillFailed() {
        return new PersonnelException(".set.skill.failed");
    }

    public static PersonnelException assessNotFound() {
        return new PersonnelException(".assess.not.found");
    }

    public static PersonnelException invlidId() {
        return new PersonnelException(".invalid.id");
    }
}
