package com.github.vedeshkin.cloud.server;

import com.github.vedeshkin.cloud.common.FileObject;
import com.github.vedeshkin.cloud.common.FileUtil;
import com.github.vedeshkin.cloud.common.request.AbstractRequest;
import com.github.vedeshkin.cloud.common.response.FileListResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Vedeshkin on 1/25/2019.
 * All right reserved.
 */
public class MainHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(MainHandler.class.getSimpleName());
    private Path cloudStorage = Paths.get("MyCloudStorage");
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)   {
        if (msg == null) return;
        AbstractRequest abstractRequest = (AbstractRequest) msg;
        switch (abstractRequest.getType()) {
            case GET_FILE:
               //
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
        System.out.println("Attempt to send file list");
        List<FileObject> filesList = FileUtil.getFileObjectList(cloudStorage);
        ctx.writeAndFlush(new FileListResponse(filesList));
    }
}
