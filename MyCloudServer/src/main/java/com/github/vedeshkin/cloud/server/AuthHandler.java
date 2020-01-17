package com.github.vedeshkin.cloud.server;

import com.github.vedeshkin.cloud.common.messages.AbstractMessage;
import com.github.vedeshkin.cloud.common.messages.AuthorizeRequest;
import com.github.vedeshkin.cloud.common.messages.AuthorizeResponse;
import com.github.vedeshkin.cloud.common.messages.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

/**
 * Created by Vedeshkin on 1/25/2019.
 * All right reserved.
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(AuthHandler.class.getSimpleName());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object data) {
        logger.entering(AuthHandler.class.getCanonicalName(), "channelRead");

        AbstractMessage message = (AbstractMessage) data;
        if (message.getMessageType() == MessageType.AUTHORIZATION_REQUEST) {
            AuthorizeRequest request = (AuthorizeRequest) message;
            UserService us = UserServiceTestImpl.getInstance();
            String userName = request.getLogin();
            String password = request.getPassword();
            if (!us.isExist(userName)) {
                ctx.writeAndFlush(new AuthorizeResponse(false, "Incorrect login or password for user " + userName));
                logger.info(String.format("User %s doesn't exist", userName));
                return;
            }

            User user = us.getUser(userName);
            if (!us.passwordIsMatch(user, password)) {
                ctx.writeAndFlush(new AuthorizeResponse(false, "Incorrect login or password for user " + userName));
                logger.info(String.format("Incorrect password for user %s ", userName));
                return;
            }

            ctx.writeAndFlush(new AuthorizeResponse(true, "User has been authorized by server"));
            ctx.pipeline().removeLast();//remove our self
            ctx.pipeline().addLast(new MainHandler(user));

        } else {

            ctx.fireChannelRead(data);
        }
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

}

