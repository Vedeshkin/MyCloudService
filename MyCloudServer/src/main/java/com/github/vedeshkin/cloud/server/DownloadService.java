package com.github.vedeshkin.cloud.server;

import com.github.vedeshkin.cloud.common.FileObject;
import com.github.vedeshkin.cloud.common.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DownloadService implements FileOperation {
    private static Logger logger = Logger.getLogger(DownloadService.class.getCanonicalName());
    private User user;
    private File outputFile;
    private boolean isNewFile = true;
    private int currentSize = 0;


    public  DownloadService(User user){
        this.user = user;
    }

    public void start(FileObject fileObject){
        logger.entering(DownloadService.class.getCanonicalName(),"start",fileObject);
        //TemporaryHook
        Path filePath = Paths.get("MyCloudStorage",user.getName(),fileObject.getFileName());
        outputFile = new File(filePath.toString());
        if(outputFile.exists()){
            //userAttempt to upload the same file?
            //delete and create a new file
            outputFile.delete();
            logger.info("File already exists, deleting");
        }
        logger.info(String.format(
                "Starting download of file %s, file size is %d, current size is %d, chunk size is %d",
                fileObject.getFileName(),
                fileObject.getSize(),
                currentSize,
                fileObject.getData().length));
        try(FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(fileObject.getData());
        }catch (IOException ex)
        {
            logger.log(Level.SEVERE,ex.getMessage(),ex);
        }
        if(fileObject.getData().length <= FileUtil.MAX_CHUNK_SIZE){
            //All file in one chunk
            logger.info("File transfer complete.\nFile size is "+fileObject.getData().length +" bytes.");
            FileService fileService = FileService.getInstance();
            fileService.removeService(user);
            return;
        }
        isNewFile = false;
        currentSize += fileObject.getData().length;
        logger.info(String.format(
                "First part of file %s has been downloaded.Current size is %d",fileObject.getFileName(),currentSize));
        logger.exiting(DownloadService.class.getCanonicalName(),"start");
    }

    public void proceedDownload(FileObject fileObject){

        logger.entering(DownloadService.class.getCanonicalName(),"proceedDownload",fileObject);

        int chunkSize =  fileObject.getData().length;
        logger.info(String.format(
                "Proceeding downloading of the file %s,  file size is %d, current size is %d, chunk size is %d",
                fileObject.getFileName(),
                fileObject.getSize(),
                currentSize,
                chunkSize));

        try (FileOutputStream fos = new FileOutputStream(outputFile,true)){
            fos.write(fileObject.getData());
        }catch (IOException ex){
            logger.log(Level.SEVERE,ex.getMessage(),ex);
        }
        currentSize += chunkSize;
        if(chunkSize <= FileUtil.MAX_CHUNK_SIZE){
            //Download complete
            logger.info(String.format(
                    "The download of file %s has been complete.File size %d",
                    fileObject.getFileName(),
                    currentSize));
            FileService.getInstance().removeService(user);
            return;
        }
        logger.info(String.format(
                "A part of file %s has been complete.File size now is %d",
                fileObject.getFileName(),
                currentSize)
                );
    }








}
