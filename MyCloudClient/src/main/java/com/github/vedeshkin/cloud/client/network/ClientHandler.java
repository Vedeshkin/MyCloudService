package com.github.vedeshkin.cloud.client.network;

import com.github.vedeshkin.cloud.client.UIHelper;
import com.github.vedeshkin.cloud.common.response.AbstractResponse;
import com.github.vedeshkin.cloud.common.response.FileListResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import javafx.application.Platform;

import java.util.logging.Logger;


/**
 * Created by Vedeshkin on 2/2/2019.
 * All right reserved.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(NetworkService.class.getSimpleName());
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("got message");

        if (msg == null) logger.info("Empty response");
        AbstractResponse message = (AbstractResponse) msg;

        switch (message.getType()){
            case FILE_LIST:
                logger.info("Got FileList from Server ");
                //TODO: add handler here
                break;
            case FILE:
                logger.info("Got File from Server");
                break;
            default:
                logger.warning("Response not found");
        }

    }


}
