package com.github.vedeshkin.cloud.server;


import com.github.vedeshkin.cloud.common.FileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserServiceTestImpl implements UserService {

    private final Logger logger = Logger.getLogger(UserServiceTestImpl.class.getSimpleName());
    private static UserServiceTestImpl instance = null;
    HashMap<String, User> users;

    private UserServiceTestImpl() {
        this.users = new HashMap<>();
        this.users.put("test", new User("test", "test", "test"));
        this.users.put("admin", new User("admin", "admin", "admin"));

    }

    public static UserServiceTestImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceTestImpl();
        }
        return instance;
    }


    public User getUser(String name) {
        return users.get(name);
    }

    @Override
    public boolean isExist(String name) {
        return this.users.containsKey(name);
    }

    /*
     * TODO:
     * Add password hashing
     * */
    @Override
    public boolean passwordIsMatch(User user, String password) {
        String userPassword = user.getPassword();
        return userPassword.equals(password);
    }

    @Override
    public User createUser(String name, String password) {
        User user = new User(name, password, name);
        Path userFileFolder = Paths.get(FileUtil.SERVER_STORAGE_ROOT, name);
        try {
            FileUtil.createFileFolderIfNotExist(userFileFolder);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, String.format("Not able to create file folder for user %s"), ex);
            return null;
        }
        this.users.put(name, user);
        return user;
    }


}
