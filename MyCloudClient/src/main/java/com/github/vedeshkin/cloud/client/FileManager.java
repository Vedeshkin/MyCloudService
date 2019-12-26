package com.github.vedeshkin.cloud.client;

import com.github.vedeshkin.cloud.client.network.NetworkService;
import com.github.vedeshkin.cloud.common.FileObject;
import com.github.vedeshkin.cloud.common.FileUtil;
import com.github.vedeshkin.cloud.common.request.FileUploadRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {
    private static FileManager instance = null;
    private final Logger logger = Logger.getLogger(FileManager.class.getSimpleName());
    private boolean isDownloadActive = false;
    private String activeDownloadFileName = null;


    private FileManager() {
    }

    public static FileManager getInstance() {
        if (instance == null) {
            instance = new FileManager();
        }
        return instance;
    }


    public void uploadFile(File file) {
        if (file == null) {
            logger.severe("File can't be null");
            return;
        }
        if (!file.exists()) {
            logger.severe(String.format("File %s doesn't exists"));
            return;
        }
        int fileLength = (int) file.length();
        String fileName = file.getName();
        byte[] data;
        if (fileLength <= FileUtil.MAX_CHUNK_SIZE) {
            //Send in one part?
            logger.info(String.format("Reading file %s , File size is %d, file is about to send in one part",
                    fileName,
                    fileLength));
            data = new byte[fileLength];
            try (FileInputStream fis = new FileInputStream(file)) {
                fis.read(data);
            } catch (IOException ex) {
                logger.severe(String.format("Exception occurred during reading of the file %s.", file.getName()));
                logger.log(Level.SEVERE, ex.getMessage(), ex);
                return;

            }
            FileObject fo = new FileObject(fileName, data, fileLength);
            FileUploadRequest fur = new FileUploadRequest(fo);
            NetworkService.getInstance().send(fur);// is it done?
            logger.info(String.format("File %s has been fully send", file.getName()));
            return;

        }
        // File is greater than the size of one chunk -> we need to send it in several parts.
        // Step one we need to initialize cycle.
        int totalParts = fileLength / FileUtil.MAX_CHUNK_SIZE;
        int currentPart = 0;
        int currentPos = 0;
        try (FileInputStream fis = new FileInputStream(file)) {
            logger.info(String.format("File size is %d, file will be sent in %d parts",
                    fileLength,
                    totalParts));

            while (currentPos != fileLength) {
                int bytesRead;
                //Last part?
                if (fileLength - currentPos < FileUtil.MAX_CHUNK_SIZE) {
                    data = new byte[fileLength - currentPos];// size of chunk;
                    bytesRead = fis.read(data, 0, fileLength - currentPos);
                } else {
                    data = new byte[FileUtil.MAX_CHUNK_SIZE];
                    bytesRead = fis.read(data, 0, FileUtil.MAX_CHUNK_SIZE);
                }
                currentPart++;
                currentPos += bytesRead; //Increment the counter of the cycle
                logger.info(String.format("Sending part %d, bytes left in the file %d, bytes already send %d",
                        currentPart,
                        fis.available(),
                        currentPos));
                FileObject fo = new FileObject(fileName, data, fileLength);
                FileUploadRequest fur = new FileUploadRequest(fo);
                NetworkService.getInstance().send(fur);

            }
        } catch (IOException iex) {
            logger.severe("Error during file reading");

        }

    }


    public void downloadFile(FileObject fo) {
        //the same part as on the server bu vise-versa :)
        logger.entering(FileManager.class.getCanonicalName(),"downloadFile",fo);
        if (!isDownloadActive){
            // fist time?
            isDownloadActive = true;
            if (fo.getSize() < FileUtil.MAX_CHUNK_SIZE) {
                downloadInOnePiece(fo);
            }
                else {
                    downloadInMultiPieces(fo);
                }
            return;

        }
        //Download already in progress.Let's check if this is our file
        if (fo.getFileName().equals(this.activeDownloadFileName)){
            proceedDownload(fo);
            return;
        }
        //This is a new file or wrong state.As of now - just drop such request:
        logger.info("Attempt to download  file while another operation is in progress");
    }

    private void proceedDownload(FileObject fo) {
    }

    private void downloadInMultiPieces(FileObject fo) {
        //So, our download is either in progress, fine, file should be already created only what we have
        //to do is just compare the output file size and the original one
        Path p = Paths.get("MyCloudStorage");
        File outputFile = new File(p.toString() + File.pathSeparator + fo.getFileName());

        try (FileOutputStream fos = new FileOutputStream(outputFile,false)){
            fos.write(fo.getData());

        }catch (IOException ex){
            logger.log(Level.SEVERE,ex.getMessage(),ex);
            return;
        }




    }

    private void downloadInOnePiece(FileObject fo) {
        //at this moment we're sure that this is a new single file.
        //so in case if previous file exists - just overwrite it
        Path p = Paths.get("MyCloudStorage");
        File outputFile = new File(p.toString() + File.pathSeparator + fo.getFileName());
        try (FileOutputStream fos = new FileOutputStream(outputFile,false)){
            fos.write(fo.getData());

        }catch (IOException ex){
            logger.log(Level.SEVERE,ex.getMessage(),ex);
            return;
        }
        logger.info(String.format("File download has been completed. \n File size - is %d", outputFile.length()));
        resetDownloadFlags();
    }

    private void resetDownloadFlags() {
        this.isDownloadActive = false;
        this.activeDownloadFileName = null;
    }

}
