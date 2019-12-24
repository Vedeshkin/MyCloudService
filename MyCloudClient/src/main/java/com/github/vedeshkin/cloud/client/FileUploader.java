package com.github.vedeshkin.cloud.client;

import com.github.vedeshkin.cloud.client.network.NetworkService;
import com.github.vedeshkin.cloud.common.FileObject;
import com.github.vedeshkin.cloud.common.FileUtil;
import com.github.vedeshkin.cloud.common.request.FileUploadRequest;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUploader  implements FileOperation{
    private File file;
    private boolean isNew = true;
    private int part;
    private int currentLenght = 0;
    private Logger logger = Logger.getLogger(FileUploader.class.getSimpleName());

    public FileUploader(File f){
        this.file = f;
    }

    public void startUpload(){
        if(file == null){
            logger.severe("File doesn't specified");
            return;
        }
        if(!file.exists()){
            logger.severe("File doesn't exist");
            return;
        }
        if(file.length() <= FileUtil.MAX_CHUNK_SIZE){
            //Send in one piece;
            byte data [] = new byte[(int)file.length()];
            try(FileInputStream fis =  new FileInputStream(file)
            ){
                int bytesRead = fis.read(data);
                logger.info(String.format("Reading file %s , File size is %d, Bytes readL %d", file.getName(),file.length(),bytesRead));
                FileObject fileObject = new FileObject(file.getName(),data,bytesRead);
                NetworkService.getInstance().send(new FileUploadRequest(fileObject));
            }catch (IOException ex){
                logger.log(Level.SEVERE,ex.getMessage(),ex);
            }
            logger.info(String.format("File %s has been fully send",file.getName()));
            FileService.getInstance().removeOperation(file);
            return;

        }

        byte data [] = new byte[(int)file.length()];
        try(FileInputStream fis =  new FileInputStream(file)
        ){
            int bytesRead = fis.read(data,0,FileUtil.MAX_CHUNK_SIZE);
            logger.info(String.format("Reading file %s , File size is %d, Bytes read %d", file.getName(),file.length(),bytesRead));
            logger.info(String.format("File will be send in %d parts",(int) file.length()/FileUtil.MAX_CHUNK_SIZE));
            FileObject fileObject = new FileObject(file.getName(),data,bytesRead);
            NetworkService.getInstance().send(new FileUploadRequest(fileObject));
            this.currentLenght+= bytesRead;
        }catch (IOException ex){
            logger.log(Level.SEVERE,ex.getMessage(),ex);
        }
        this.part++;
        this.isNew = false;
        return;

    }


    public void proceed() {
        if(this.isNew){
            logger.severe("File hasn't been sent yet.Nothing to procced with");
            startUpload();
            return;
        }
        // check if this is a last part
        int bytesLeft = (int)this.file.length() - this.currentLenght;

    }
}
