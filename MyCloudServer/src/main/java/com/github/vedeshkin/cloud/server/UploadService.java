package com.github.vedeshkin.cloud.server;

import com.github.vedeshkin.cloud.common.FileObject;
import com.github.vedeshkin.cloud.common.FileUtil;
import com.github.vedeshkin.cloud.common.response.FileDownloadResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UploadService implements  FileOperation {
    private File fileToUpload;
    private int filePart = 0;
    private int fileSize;
    private User user;
    private boolean isNew;
    private int currentSize = 0;
    private ChannelHandlerContext ctx;
    private Logger logger =  Logger.getLogger(UploadService.class.getCanonicalName());

    public UploadService(User user, ChannelHandlerContext ctx) {
        this.ctx = ctx;
        this.user = user;
    }


    public void start(String fileName) {
        logger.entering(UploadService.class.getCanonicalName(), "start", fileName);
        //TemporaryHook
        Path filePath = Paths.get("MyCloudStorage", user.getName(), fileName);
        fileToUpload = new File(filePath.toString());
        if (!fileToUpload.exists()) {
            logger.severe(String.format("File %s doesn't exist", fileName));
            return;
        }
        fileSize = (int) fileToUpload.length();
        logger.info(String.format(
                "File % is about to be uploaded to the client %s.File size is %d.According to the current chunk size %d,file will be send in %d parts",
                fileName,
                user.getName(),
                fileSize,
                FileUtil.MAX_CHUNK_SIZE,
                fileSize / FileUtil.MAX_CHUNK_SIZE));

        if (fileSize <= FileUtil.MAX_CHUNK_SIZE) {
            //file size  is less then one chunk, transfer in one packet
            byte[] fileBody = new byte[fileSize];
            try (FileInputStream fos = new FileInputStream(fileToUpload);
                 BufferedInputStream bis = new BufferedInputStream(fos)) {
                bis.read(fileBody);
            } catch (IOException ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
                return;
            }
            FileObject objectToUpload = new FileObject(fileName, fileBody, fileSize);
            ctx.writeAndFlush(new FileDownloadResponse(objectToUpload));
            logger.info(String.format("File %s has bee sent to the client", fileName));
            FileService.getInstance().removeService(user);
            return;
        }

        byte[] fileBody = new byte[FileUtil.MAX_CHUNK_SIZE];
        try (FileInputStream fos = new FileInputStream(fileToUpload);
             BufferedInputStream bis = new BufferedInputStream(fos)) {
            currentSize += bis.read(fileBody);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return;
        }
        FileObject objectToUpload = new FileObject(fileName, fileBody, fileSize);
        ctx.writeAndFlush(new FileDownloadResponse(objectToUpload));
        isNew = false;
        filePart++;
        return;
    }


    public void proceedUpload() {
        logger.info(String.format(
                "Sending part # %d of file %s to the client %s.",
                filePart,
                this.fileToUpload.getName(),
                user.getName()));
        int bytesLeft = this.fileSize - this.currentSize;
        int bytesSend = 0;
        byte fileBody[] = new byte[bytesLeft >= FileUtil.MAX_CHUNK_SIZE?FileUtil.MAX_CHUNK_SIZE:bytesLeft];

        try(FileInputStream fos = new FileInputStream(fileToUpload);
            BufferedInputStream bis = new BufferedInputStream(fos)){
            bytesSend =  bis.read(fileBody,currentSize,bytesLeft >= FileUtil.MAX_CHUNK_SIZE?FileUtil.MAX_CHUNK_SIZE:bytesLeft);

        }catch (IOException ex){
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return;
        }
        FileObject objectToUpload = new FileObject(this.fileToUpload.getName(), fileBody, fileSize);
        ctx.writeAndFlush(new FileDownloadResponse(objectToUpload));
        filePart++;
        currentSize += bytesSend;
        if(fileSize == currentSize){
            logger.info(String.format("File %s has been sent to the client", this.fileToUpload.getName()));
            FileService.getInstance().removeService(user);
            return;
        }
        logger.info(String.format(
                "Part # %d of file %s has been send to the client.Part size is %d.",
                this.filePart,
                this.fileToUpload.getName(),
                bytesSend));
        return;

    }
}
