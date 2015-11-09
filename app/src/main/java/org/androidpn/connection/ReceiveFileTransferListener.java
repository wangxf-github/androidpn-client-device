package org.androidpn.connection;

import android.util.Log;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;

import java.io.File;

/**
 * Created by Administrator on 7/20/2015.
 */
public class ReceiveFileTransferListener implements FileTransferListener {

    @Override
    public void fileTransferRequest(FileTransferRequest fileTransferRequest) {
        IncomingFileTransfer accept = fileTransferRequest.accept();
        File file = new File("/data" + fileTransferRequest.getFileName());
        try {
            accept.recieveFile(file);
        } catch (XMPPException e) {
            Log.e("Update apk","接收文件失败",e);
        }

    }
}
