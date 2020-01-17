package com.github.vedeshkin.cloud.server;

public interface UserService {

    User getUser(String name);

    boolean isExist(String name);

    boolean passwordIsMatch(User user, String password);

    User createUser(String name, String password);


}
