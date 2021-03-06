package com.github.vedeshkin.cloud.client.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.logging.Logger;

/**
 * Created by Vedeshkin on 1/25/2019.
 * All right reserved.
 */
public class NetworkService {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 11111;
    static final int MAX_OBJECT_SIZE = 1024 * 1024 * 50;
    private Channel channel;
    private static NetworkService instance;

    private static final Logger logger = Logger.getLogger(NetworkService.class.getSimpleName());

    private NetworkService() {
    }

    public static NetworkService getInstance() {
        if (instance == null) {
            instance = new NetworkService();
            instance.init();
        }
        return instance;

    }

    public void send(Object msg) {
        this.channel.writeAndFlush(msg);
    }

    public Channel getChannel() {
        return this.channel;
    }

    private void init() {


        NioEventLoopGroup workGroup = new NioEventLoopGroup();// working threads for handling connections;

        Bootstrap clientBootstrap = new Bootstrap();
        clientBootstrap.group(workGroup);
        clientBootstrap.channel(NioSocketChannel.class);
        clientBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        clientBootstrap.handler(new ChannelInitializer<io.netty.channel.Channel>() {
            @Override
            protected void initChannel(io.netty.channel.Channel ch) throws Exception {
                ch.pipeline()
                        .addLast(new ObjectDecoder(MAX_OBJECT_SIZE, ClassResolvers.cacheDisabled(null)), new ObjectEncoder(), new ClientHandler());

            }
        });
        ChannelFuture future = clientBootstrap.connect(HOST, PORT);
        this.channel = future.channel();
        logger.info("Connected");
        future.syncUninterruptibly();
        if (future.isDone()) {
            if (future.cause() != null) {
                future.cause().printStackTrace();
                System.out.println("something went wrong");

            }
            if (future.isSuccess()) {
                System.out.println(future.channel().localAddress());

            }
        }

    }


}
