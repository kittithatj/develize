package com.pim.develize.exception;

public class PersonnelException extends BaseException{

    public PersonnelException(String code) {
        super("personnel" + code);
    }

    public static PersonnelException CreateFailed() {
        return new PersonnelException(".create.failed");
    }
}
