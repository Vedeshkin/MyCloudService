package com.github.vedeshkin.cloud.server;

import java.util.HashMap;


public class UserService {

    private static  UserService instance = null;
    HashMap<String, User> users;

    private UserService(){
        this.users = new HashMap<>();

    }

    public static UserService getInstance() {
        if( instance == null){
            instance = new UserService();
        }
        return instance;
    }

    public boolean authorize(String username, String pwd, String s) {
        //temporary hack
        User user = new User(username);
        users.put(s,user);
        return  true;
    }

    public User getUser(String chanelID){
        return  users.get(chanelID);
    }
}
