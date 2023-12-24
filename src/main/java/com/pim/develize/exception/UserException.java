package com.pim.develize.exception;

public class UserException extends BaseException {
    public UserException(String code) {
        super("user" + code);
    }

    public static UserException loginFailed() {
        return new UserException("login.failed");
    }

    public static UserException registerFailed() {
        return new UserException("register.failed");
    }

    public static UserException authorizeFailed() {
        return new UserException("authorize.failed");
    }

    public static UserException notFound() {
        return new UserException("not.found");
    }

    public static UserException noPermission() {
        return new UserException(".no.permission");
    }
}
