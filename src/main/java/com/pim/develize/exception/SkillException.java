package com.pim.develize.exception;

public class SkillException extends BaseException{

    public SkillException(String code) {
        super("skill" + code);
    }

    public static SkillException skillNameBlank() {
        return new SkillException("name.isBlank");
    }

    public static SkillException NullRequestPost() {
        return new SkillException("req.null");
    }

}
