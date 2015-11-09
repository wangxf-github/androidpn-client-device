package org.androidpn.update;

import android.util.Log;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.stream.StreamIoHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Copyright (c) 2015, ZSmarter All Rights Reserved. Package
 * Name:org.androidpn.test File Name:ClientStreamIoHandler.java
 * @author zhaojunyan Date:Jul 20, 20151:46:10 PM
 *         ClassName:ClientStreamIoHandler <br/>
 */
public class ClientStreamIoHandler extends StreamIoHandler{
    
    private  int i = 0;

    @Override
    public void sessionOpened(IoSession session) {
        Log.i("ClientStreamIoHandler", "sessionOpened() ~~~~~~~~~~~~");
        super.sessionOpened(session);
    }

    @Override
    protected void processStreamIo(IoSession session, InputStream in, OutputStream out){

        // 客户端发送文件
        File recieveFile = new File("/storage/sdcard0/aa.zip");
        if(!recieveFile.exists()){
            try {
                recieveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        recieveFile.setWritable(true);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(recieveFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 放入线程让其执行
        // 客户端一般都用一个线程实现即可 不用线程池
        new IoStreamThreadWork(in, fos).start();

        return;
    }
}
