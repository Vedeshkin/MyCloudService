package com.github.vedeshkin.cloud.server;

import com.github.vedeshkin.cloud.common.FileObject;
import io.netty.channel.ChannelHandlerContext;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileService {
    private Path cloudStorage = Paths.get("MyCloudStorage");
    private HashMap<User, FileOperation> services;

    private FileService(
    ) {
        this.services = new HashMap<>();

    }

    private static FileService instance = null;

    public static FileService getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new FileService();
        return instance;
    }

    public void downloadFile(User user, FileObject fileObject) {
        if (!services.containsKey(user)) {
            DownloadService ds = new DownloadService(user);
            services.put(user, ds);
            ds.start(fileObject);
            return;
        }
        DownloadService ds = (DownloadService) services.get(user);
        ds.proceedDownload(fileObject);
    }

    public void removeService(User user){
        this.services.remove(user);
    }


    public void uploadFile(ChannelHandlerContext ctx, User user, String fileName) {
        if(!services.containsKey(user)){
            UploadService us = new UploadService(user,ctx);
            services.put(user, us);
            us.start(fileName);
        }
        UploadService up = (UploadService)services.get(user);
        up.proceedUpload();

    }
}
