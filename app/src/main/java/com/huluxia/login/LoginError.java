package com.huluxia.login;

public abstract class LoginError extends Exception {
    protected LoginErrCode code;

    public enum LoginErrCode {
        REGISTER_UNBOUND,
        REGISTER_REMOTE_EX,
        REGISTER_STATUS_1,
        REGISTER_STATUS_0,
        REGISTER_JSON_ERROR,
        REGISTER_RESPONSE_ERROR,
        REGISTER_CALLBACK_REMOTE_EX,
        REGISTER_CLIENTID_ERROR,
        REGISTER_ARGUEMENT_ERROR,
        LOGIN_UNBOUND,
        LOGIN_REMOTE_EX,
        LOGIN_STATUS_1,
        LOGIN_STATUS_0,
        LOGIN_JSON_ERROR,
        LOGIN_RESPONSE_ERROR,
        LOGIN_CLIENT_VERIFY_ERROR,
        LOGIN_INVALID_ARGUEMENT_ERROR,
        LOGIN_CLIENTID_ERROR,
        LOGIN_ARGUEMENT_ERROR,
        AUTO_LOGIN_UNBOUND,
        AUTO_LOGIN_VERIFY_ERROR,
        AUTO_LOGIN_PERSIST_ACCOUNT_ERROR,
        AUTO_LOGIN_CLIENTID_ERROR,
        AUTO_LOGIN_ARGUEMENT_ERROR,
        AUTO_LOGIN_REMOTE_EX,
        AUTO_LOGIN_NONE,
        LOGOUT_CLIENTID_ERROR,
        LOGOUT_ARGUEMENT_ERROR,
        LOGOUT_REMOTE_EX
    }

    public LoginError(LoginErrCode code) {
        this.code = code;
    }

    public LoginError(String message, LoginErrCode code) {
        super(message);
        this.code = code;
    }
}
