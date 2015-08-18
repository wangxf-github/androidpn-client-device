package update;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

/**
 * Copyright (c) 2015, ZSmarter All Rights Reserved. Package
 * Name:org.androidpn.test File Name:Client.java
 * @author zhaojunyan Date:Jul 20, 20151:43:13 PM ClassName:Client <br/>
 */
public class Client{
    
    public Client(){
        super();
    }
    
    public void connect() throws InterruptedException{
        NioSocketConnector connector = new NioSocketConnector();
        ObjectSerializationCodecFactory factory = new ObjectSerializationCodecFactory();
        factory.setDecoderMaxObjectSize(Integer.MAX_VALUE);
        factory.setEncoderMaxObjectSize(Integer.MAX_VALUE);
        // Configure the service.
        connector.setConnectTimeoutMillis(6000);
        // connector.getFilterChain().addLast("codec",new
        // ProtocolCodecFilter(factory));
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.setHandler(new ClientStreamIoHandler());
        IoSession session;
        for ( ;;) {
            try {
                ConnectFuture future = connector.connect(new InetSocketAddress("192.168.12.23", 6222));
                future.awaitUninterruptibly();
                session = future.getSession();
                break;
            } catch (RuntimeIoException e) {
                System.err.println("Failed to connect.");
                e.printStackTrace();
                Thread.sleep(5000);
            }
        }
        // wait until the summation is done
        session.getCloseFuture().awaitUninterruptibly();
        connector.dispose();
    }
    
    /**
     * 
     * @param args
     * 
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException{
        Client client = new Client();
        client.connect();
    }
}
