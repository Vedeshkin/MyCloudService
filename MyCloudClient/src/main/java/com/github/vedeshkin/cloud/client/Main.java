package com.github.vedeshkin.cloud.client;

import com.github.vedeshkin.cloud.client.network.InboundClientHandler;
import com.github.vedeshkin.cloud.common.request.FileListRequest;
import com.github.vedeshkin.cloud.common.response.FileListResponse;
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
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



/**
 * Created by Vedeshkin on 1/22/2019.
 * All right reserved.
 */
public class Main extends Application {



    @Override
    public void init() throws Exception {
       //do something useful here?

    }

    @Override
    public void start(Stage stage) throws Exception {
        //show login
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent login = fxmlLoader.load();
        stage.setTitle("Login");
        Scene scene = new Scene(login);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
