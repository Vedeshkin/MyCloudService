package com.github.vedeshkin.cloud.client.network;

import com.github.vedeshkin.cloud.common.request.AbstractRequest;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;

public class OutBoundClientHandler extends ChannelOutboundHandlerAdapter {

    ChannelHandlerContext context;

    public void sent(AbstractRequest request) {
        if (context != null) {
            ChannelFuture channelFuture = context.writeAndFlush(request);
            if (channelFuture.isDone()) {

            }
        }
    }
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }


}
