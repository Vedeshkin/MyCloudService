package com.github.vedeshkin.cloud.common.messages;


public class AuthorizeRequest extends AbstractMessage {

    private final String login;
    private final String password;

    public AuthorizeRequest(String login, String password) {
        super(MessageType.AUTHORIZE);
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
