package com.pim.develize.exception;

public class ProjectException extends BaseException{

    public ProjectException(String code) {
        super("project" + code);
    }

    public static ProjectException CreateFailed() {
        return new ProjectException(".create.failed");
    }

    public static ProjectException ProjectNotFound() {
        return new ProjectException(".not.found");
    }

}
