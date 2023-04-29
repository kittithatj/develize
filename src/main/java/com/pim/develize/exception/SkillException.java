package com.pim.develize.exception;

public class SkillException extends BaseException{

    public SkillException(String code) {
        super("skill" + code);
    }

    public static SkillException skillNameBalnk() {
        return new SkillException("name.isBlank");
    }

}
