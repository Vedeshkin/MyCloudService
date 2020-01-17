package com.github.vedeshkin.cloud.server;

public class User {
    private String name;
    private String password;

    public String getPath() {
        return path;
    }

    private String path;

    public User(String name, String password, String path) {
        this.name = name;
        this.password = password;
        this.path = path;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return this.name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
