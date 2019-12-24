package com.github.vedeshkin.cloud.client;

import java.io.File;
import java.util.HashMap;

public class FileService {
    private static  FileService instance  = null;
    private HashMap<String ,FileOperation> operations = new HashMap<>();

    private FileService(){}

    public static FileService getInstance(){
        if (instance == null) {
            instance = new FileService();
        }
        return instance;
    }

    public void removeOperation(File file) {
        operations.remove(file);
    }

    public void uploadFile(File file){
        if (operations.containsKey(file.toString())){
              FileUploader fu =  (FileUploader) operations.get(file.toString());
              fu.proceed();
        }
    }


}
