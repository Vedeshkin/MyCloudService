package com.github.vedeshkin.cloud.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserService {

    private final  Logger logger = Logger.getLogger(UserService.class.getSimpleName());
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
        createFileFolderIfNotExist(user);
        return  true;
    }

    public User getUser(String chanelID){
        return  users.get(chanelID);
    }

    private void createFileFolderIfNotExist(User user){
        Path p = Paths.get("MyCloudStorage",user.getName());
        File f = new File(p.toString());
        if (!f.exists()){
            try{
                Files.createDirectories(p);
            }catch (IOException ex){
                logger.severe("Not able to create user directory");
                logger.log(Level.SEVERE,ex.getMessage(),ex);

            }
        }

    }
}
