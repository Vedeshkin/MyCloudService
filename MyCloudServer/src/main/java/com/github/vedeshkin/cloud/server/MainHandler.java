package com.github.vedeshkin.cloud.server;


import com.github.vedeshkin.cloud.common.FileInfo;
import com.github.vedeshkin.cloud.common.FileObject;
import com.github.vedeshkin.cloud.common.FileService;
import com.github.vedeshkin.cloud.common.FileUtil;
import com.github.vedeshkin.cloud.common.request.AbstractRequest;
import com.github.vedeshkin.cloud.common.request.FileDownloadRequest;
import com.github.vedeshkin.cloud.common.request.FileUploadRequest;
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

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg == null) return;
        UserService userService = UserService.getInstance();
        User user = userService.getUser(ctx.channel().id().asLongText());

        AbstractRequest abstractRequest = (AbstractRequest) msg;
        switch (abstractRequest.getType()) {
            case FILE_UPLOAD:
                logger.info("File upload packet recognized");
                FileUploadRequest fileUpload = (FileUploadRequest) abstractRequest;
                FileObject fo = fileUpload.getFileObject();
                //TODO: Path should be resolved in another way
                Path pathToDownload = Paths.get("MyCloudStorage", user.getName(), fo.getFileName());
                FileService.getInstance().downloadFile(pathToDownload, fileUpload.getFileObject());
                break;

            case FILE_DOWNLOAD:
                logger.info("File download packet recognized");
                FileDownloadRequest fileDownloadRequest = (FileDownloadRequest) abstractRequest;
                Path pathToUpload = Paths.get("MyCloudStorage", user.getName(), fileDownloadRequest.getFileName());
                FileService.getInstance().uploadFile(ctx.channel(), pathToUpload);
                break;


            case GET_FILE_LIST:
                logger.info("File list packet recognized");
                List<FileInfo> fileInfoList = FileUtil.getFileObjectList(Paths.get("MyCloudStorage", user.getName()));
                FileListResponse fileListResponse = new FileListResponse(fileInfoList);
                ctx.writeAndFlush(fileListResponse);
                break;

            default:
                System.out.println(msg.toString());

        }
        ReferenceCountUtil.release(msg);
    }

}
