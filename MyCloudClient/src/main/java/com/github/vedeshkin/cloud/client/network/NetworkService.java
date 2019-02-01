package com.github.vedeshkin.cloud.client.network;

import com.github.vedeshkin.cloud.common.request.AbstractRequest;
import com.github.vedeshkin.cloud.common.response.AbstractResponse;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Created by Vedeshkin on 1/25/2019.
 * All right reserved.
 */
public class NetworkService {
    private static NetworkService networkServiceInstance;
    private Socket socket;
    private ObjectEncoderOutputStream out;
    private ObjectDecoderInputStream in;
    private static final Logger logger = Logger.getLogger(NetworkService.class.getSimpleName());

    private  NetworkService(){}

    public static NetworkService getInstance(){
        if (networkServiceInstance == null) {
            networkServiceInstance = new NetworkService();
            try {
                networkServiceInstance.init();
            } catch (IOException iex) {
                logger.severe("Network init failed");
                logger.severe(iex.getMessage());
                System.exit(-1);
            }
        }
        return networkServiceInstance;

    }

    private void init() throws IOException {
        logger.info("Attempt to connect to server");
        this.socket = new Socket("127.0.0.1",11111);
        this.in = new ObjectDecoderInputStream(socket.getInputStream(),10*1024*1024);
        this.out = new ObjectEncoderOutputStream(socket.getOutputStream(),10*1024*1024);
        logger.info("Connected to the server;");

    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        }catch (IOException iex)
        {
            iex.printStackTrace();
        }
    }

    public void sendRequest(AbstractRequest abstractRequest){
        try{
            out.writeObject(abstractRequest);
            out.flush();
        }catch (IOException iex)
        {

            logger.severe("Failed to sent abstractRequest" + abstractRequest);
            logger.severe(iex.getMessage());
            iex.printStackTrace();

        }
    }
    public AbstractResponse readResponse(){
        AbstractResponse res = null;
        try {
            res = (AbstractResponse) in.readObject();
        }catch (IOException|ClassNotFoundException ex){
            logger.severe("Failed  to get  resp");
            logger.severe(ex.getMessage());
            ex.printStackTrace();

        }

        return res;
    }


}
