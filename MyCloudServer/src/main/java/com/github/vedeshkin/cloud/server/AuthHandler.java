package com.github.vedeshkin.cloud.server;

import com.github.vedeshkin.cloud.common.AuthorizationStatus;
import com.github.vedeshkin.cloud.common.messages.AbstractMessage;
import com.github.vedeshkin.cloud.common.messages.MessageType;
import com.github.vedeshkin.cloud.common.messages.AuthorizeResponse;
import com.github.vedeshkin.cloud.common.messages.AuthorizeRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Vedeshkin on 1/25/2019.
 * All right reserved.
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(AuthHandler.class.getSimpleName());
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object data) {
        logger.entering(AuthHandler.class.getCanonicalName(),"channelRead");

        try {
            AbstractMessage message = (AbstractMessage) data;
            if (message.getMessageType() == MessageType.AUTHORIZE) {
                AuthorizeRequest authorizeRequest = (AuthorizeRequest) message;
                UserService us = UserService.getInstance();
                if(!us.authorize(
                        authorizeRequest.getLogin(),
                        authorizeRequest.getPassword(),
                        ctx.channel().id().asLongText()
                        )){
                    ctx.writeAndFlush(new AuthorizeResponse(AuthorizationStatus.NOT_AUTHORIZED,"ERROR"));
                    ctx.channel().disconnect();
                    ctx.channel().close();
                    return;
                }

                ctx.pipeline().removeLast();//remove our self
                ctx.pipeline().addLast(new MainHandler());

            } else {
                ctx.fireChannelRead(data);
            }
        }
        catch (ClassCastException ex) {
            logger.log(Level.SEVERE,ex.getMessage(),ex);
            return;
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

