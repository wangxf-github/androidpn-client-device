package org.androidpn.client;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.bytestreams.socks5.provider.BytestreamsProvider;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferNegotiator;
import org.jivesoftware.smackx.provider.StreamInitiationProvider;

/**
 * Created by Administrator on 7/22/2015.
 */
public class FilePushTool {

    private static XMPPConnection connection;
    private static FileTransferManager fileTransferManager;

    public static  void   initFileTransport()
    {
            if(connection != null && connection.isConnected()){
                //fileTransferManager = new FileTransferManager(connection);//网上的放在这里，这回导致Asmack崩溃
                ServiceDiscoveryManager sdManager= ServiceDiscoveryManager.getInstanceFor(connection);
                if(sdManager==null)
                {
                    sdManager=new ServiceDiscoveryManager(connection);
                }
                sdManager.addFeature("http://jabber.org/protocol/disco#info");
                sdManager.addFeature("jabber:iq:privacy");
                FileTransferNegotiator.setServiceEnabled(connection, true);
                fileTransferManager = new FileTransferManager(connection);//在0.8.1.1版本（之后应该也是如此），正确的应该是放在这里。
            }

    }

    public static void buildConnection(){
        ConnectionConfiguration connConfig = new ConnectionConfiguration("192.168.12.23", 6222);
        connConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        connConfig.setSASLAuthenticationEnabled(false);
        connConfig.setCompressionEnabled(false);


        XMPPConnection connection = new XMPPConnection(connConfig);

        try {

            connection.connect();

            // FileTransfer
            ProviderManager.getInstance().addIQProvider("si",
                    "http://jabber.org/protocol/si",
                    new StreamInitiationProvider());
            ProviderManager.getInstance().addIQProvider("query",
                    "http://jabber.org/protocol/bytestreams",
                    new BytestreamsProvider());
            initFileTransport();
            fileTransferManager.addFileTransferListener(new ReceiveFileTransferListener());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
