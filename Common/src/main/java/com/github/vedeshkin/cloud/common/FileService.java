package com.github.vedeshkin.cloud.common;

import com.github.vedeshkin.cloud.common.messages.FileMessage;
import com.github.vedeshkin.cloud.common.messages.ResponseMessage;
import io.netty.channel.Channel;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileService {
    private Path cloudStorage = Paths.get("MyCloudStorage");
    private byte[] data = new byte[FileUtil.MAX_CHUNK_SIZE];
    private Logger logger = Logger.getLogger(FileService.class.getSimpleName());
    private static FileService instance = null;

    private FileService() {
    }


    public static FileService getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new FileService();
        return instance;
    }

    public void downloadFile(Path path, FileObject fileObject) {

        downloadFile0(path, fileObject);


    }

    private void downloadFile0(Path downloadPath, FileObject fileObject) {

        boolean appendFlag = !(fileObject.isFirstPart());
        int filePart = fileObject.getPartNumber();
        int fileLength = fileObject.getFileLength();
        int currentChunkLength = fileObject.getData().length;
        byte[] data = fileObject.getData();

        logger.info(String.format("Writing part #%d of file %s.File size is %d, part size is %d \n",
                filePart,
                downloadPath.toString(),
                fileLength,
                currentChunkLength
        ));

        try (FileOutputStream fos = new FileOutputStream(downloadPath.toFile(), appendFlag)) {
            fos.write(data);

        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }


    public void uploadFile(Channel channel, Path uploadPath) {

        if (!Files.exists(uploadPath)) {
            channel.writeAndFlush(new ResponseMessage(String.format("File %s not found!", uploadPath.getFileName())));
            logger.info(String.format("The requested file %s was not found on", uploadPath.getFileName()));
            return;
        }

        uploadFile0(channel,uploadPath);

    }

    private void uploadFile0(Channel channel,Path uploadPath) {
        File file = uploadPath.toFile();
        int fileLength = (int) file.length();
        int partCounter = 0;
        int currentPos = 0;
        byte[] data = this.data;

        logger.info(String.format(
                "File %s is about to be sent.File size is %d.According to the current chunk size %d,file will be send in %d parts",
                file.getName(),
                fileLength,
                FileUtil.MAX_CHUNK_SIZE,
                fileLength / FileUtil.MAX_CHUNK_SIZE));

        try (FileInputStream fis = new FileInputStream(file)) {

            while (currentPos != fileLength) {
                int bytesRead;

                if (fileLength <= FileUtil.MAX_CHUNK_SIZE) {
                    data = new byte[fileLength];
                }

                if (fileLength - currentPos < FileUtil.MAX_CHUNK_SIZE) {
                    data = new byte[fileLength - currentPos];
                }

                bytesRead = fis.read(data, 0, data.length);
                FileObject fo = new FileObject(file.getName(), data, fileLength, partCounter);
                FileMessage fdr = new FileMessage(fo);
                channel.writeAndFlush(fdr);
                logger.info(String.format(
                        "Sending part %d, bytes left in the file %d, bytes already send %d",
                        partCounter,
                        fis.available(),
                        currentPos));

                partCounter++;
                currentPos += bytesRead;
            }


        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);

        }


    }

}
