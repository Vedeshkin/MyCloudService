package com.github.vedeshkin.cloud.common.request;

import java.io.Serializable;

public class AuthorizeRequest extends AbstractRequest {

    private final String login;
    private final String password;

    public AuthorizeRequest(String login, String password) {
        super(RequestType.AUTHORIZE);
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
