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

    public static SkillException skillNotFound() { return new SkillException("not.found"); }

    public static SkillException deleteFailed() {
        return new SkillException("delete.failed");
    }

}
