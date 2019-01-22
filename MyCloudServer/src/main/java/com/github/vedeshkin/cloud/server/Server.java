package com.github.vedeshkin.cloud.server;

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
    }

    private void  loadProperties()
    {
        properties = new Properties();
        try(InputStream inputStream  = Server.class.getClassLoader().getResourceAsStream("./server.properties")){
            properties.load(inputStream);
        }catch (IOException ie){
            logger.severe(ie.getMessage());
            logger.severe(ie.getStackTrace().toString());
        }
        logger.info("Properties loaded");

    }



}
