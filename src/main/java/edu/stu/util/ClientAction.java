package edu.stu.util;

import edu.stu.bean.ClientInfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;

public class ClientAction {

    public ClientInfo getClientInfo(String userName, Boolean tag) {
        return new ClientInfo(userName, getIpInfo(), new Date(), tag);
    }

    private String getIpInfo() {
        StringBuilder ip = new StringBuilder("");
        Enumeration<NetworkInterface> es = null;
        try {
            es = NetworkInterface.getNetworkInterfaces();
            while (es.hasMoreElements()) {
                NetworkInterface networkInterface = es.nextElement();
                ip.append(networkInterface.toString());
                ip.append("\n");
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    ip.append(address.getHostAddress());
                    ip.append("\n");
                }

                ip.append("\n");
            }
        } catch (SocketException e) {
            //e.printStackTrace();
        }


        return ip.toString();

    }


}
