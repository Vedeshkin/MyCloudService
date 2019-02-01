package com.github.vedeshkin.cloud.server;

import com.github.vedeshkin.cloud.common.request.AbstractRequest;
import com.github.vedeshkin.cloud.common.request.RequestType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Created by Vedeshkin on 1/25/2019.
 * All right reserved.
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(AuthHandler.class.getSimpleName());

    private static final Path STORAGE = Paths.get("MyCloudServer/cloud_storage");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object data) {
        try {
            if (data == null) return;

            AbstractRequest abstractRequest = (AbstractRequest) data;
            if (abstractRequest.getType() == RequestType.AUTHORIZE) {

                logger.info("Authorized!");

                ctx.pipeline().addLast(new MainHandler());
            } else {
                ctx.fireChannelRead(data);
            }
        } finally {
            ReferenceCountUtil.release(data);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)   {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)   {
        ctx.close();
    }

}

