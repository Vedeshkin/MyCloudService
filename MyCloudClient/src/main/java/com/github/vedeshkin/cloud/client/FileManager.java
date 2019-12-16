package com.github.vedeshkin.cloud.client;

import com.github.vedeshkin.cloud.client.network.NetworkService;
import com.github.vedeshkin.cloud.common.FileObject;
import com.github.vedeshkin.cloud.common.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Vedeshkin on 2/8/2019.
 * All right reserved.
 */
public class FileManager {


    public void sendFile(Path path) {


        long fileSize = 0;
        byte []  data;
        try {
            fileSize = Files.size(path);
            if (fileSize > FileUtil.MAX_MESSAGE_SIZE)
            {

            }
            else {

                data = Files.readAllBytes(path);


                NetworkService networkService = NetworkService.getInstance();


                FileObject fileObject = new FileObject(path.getFileName().toString(),data,0,0,(int)fileSize);


                networkService.send();


            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
