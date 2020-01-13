package com.github.vedeshkin.cloud.client.network;

import com.github.vedeshkin.cloud.client.UIHelper;
import com.github.vedeshkin.cloud.common.FileObject;
import com.github.vedeshkin.cloud.common.FileService;
import com.github.vedeshkin.cloud.common.messages.AbstractMessage;
import com.github.vedeshkin.cloud.common.messages.FileMessage;
import com.github.vedeshkin.cloud.common.messages.FileListResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import javafx.application.Platform;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;


/**
 * Created by Vedeshkin on 2/2/2019.
 * All right reserved.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(NetworkService.class.getSimpleName());
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)  {

        if (msg == null) logger.info("Empty response");
        AbstractMessage message = (AbstractMessage) msg;

        switch (message.getMessageType()){
            case FILE_LIST:
                logger.info("Got FileList from Server ");
                FileListResponse fileListResponse = (FileListResponse) message;
                Platform.runLater(() -> UIHelper.getRemoteFileList().setAll(fileListResponse.getFileList()));
                break;
            case FILE:
                logger.info("Got File from Server");
                FileMessage fileMessage = (FileMessage)message;
                FileObject fo = fileMessage.getFileObject();
                //TODO:Create normal mechanism of path resolving the path
                Path pathDownload = Paths.get("MyLocalStorage",fo.getFileName());
                FileService.getInstance().downloadFile(pathDownload,fo);
                break;
            default:
                logger.warning("Response not found");
        }

    }


}
