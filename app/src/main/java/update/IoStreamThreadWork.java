package update;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Copyright (c) 2015, ZSmarter All Rights Reserved. Package
 * Name:org.androidpn.server.test File Name:IoStreamThreadWork.java
 * @author zhaojunyan Date:Jul 20, 201512:11:58 PM ClassName:IoStreamThreadWork <br/>
 */
public class IoStreamThreadWork extends Thread{
    
    public static final int BUFFER_SIZE = 1024*16;
    private BufferedInputStream bis;
    private BufferedOutputStream bos;
    
    public BufferedInputStream getBis(){
        return bis;
    }
    
    public void setBis(BufferedInputStream bis){
        this.bis = bis;
    }
    
    public BufferedOutputStream getBos(){
        return bos;
    }
    
    public void setBos(BufferedOutputStream bos){
        this.bos = bos;
    }
    
    public IoStreamThreadWork(InputStream in, OutputStream os){
        bis = new BufferedInputStream(in);
        bos = new BufferedOutputStream(os);
    }
    
    public synchronized void run(){
        byte[] bufferByte = new byte[BUFFER_SIZE];
        int tempData = 0;
        try {
            while ( (tempData = bis.read(bufferByte)) != -1) {
                bos.write(bufferByte, 0, tempData);
            }
            try {
                bos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                    bos.close();
                    bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
