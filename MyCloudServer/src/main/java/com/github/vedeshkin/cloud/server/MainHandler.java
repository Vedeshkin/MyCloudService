package com.github.vedeshkin.cloud.server;

import com.github.vedeshkin.cloud.common.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.util.logging.Logger;

/**
 * Created by Vedeshkin on 1/25/2019.
 * All right reserved.
 */
public class MainHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(MainHandler.class.getSimpleName());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg == null) return;
        Request request = (Request) msg;
        switch (request.getRequests()) {
            case GET_FILE:
                sendFile(ctx, request.getData());
                logger.info("requested file ");
                break;
            case GET_FILE_LIST:
                sendFileList(ctx);
                logger.info("requested file list");
                break;

        }
        ReferenceCountUtil.release(msg);
    }

    private void sendFile(ChannelHandlerContext ctx, Object data) {
    }

    private void sendFileList(ChannelHandlerContext ctx) {
    }
}
