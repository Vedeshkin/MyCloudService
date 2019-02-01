package com.github.vedeshkin.cloud.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Vedeshkin on 1/22/2019.
 * All right reserved.
 */
public class Server {
    private static Logger logger = Logger.getLogger(Server.class.getCanonicalName());
    private Properties properties;//all global settings

    public static void main(String[] args) {
        Server s = new Server();
        s.loadProperties();
        try {
            s.run();
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
    }


    private void run() throws Exception {
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(mainGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(
                                    new ObjectDecoder(10 * 1024 * 1024, ClassResolvers.cacheDisabled(null)),
                                    new ObjectEncoder(),
                                    new MainHandler()
                            );
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = b.bind(11111).sync();
            future.channel().closeFuture().sync();
        } finally {
            mainGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    private void loadProperties() {
        properties = new Properties();
        try (InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("./server.properties")) {
            properties.load(inputStream);
        } catch (IOException ie) {
            logger.severe(ie.getMessage());
            ie.printStackTrace();
        }
        logger.info("Properties are loaded");

    }


}
