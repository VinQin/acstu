package edu.stu.util;

import edu.stu.bean.ClientInfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Properties;


public class ClientActionTest {


    public static void main(String[] args) {
        //ClientInfo client = new ClientAction().getClientInfo("16fguo", true);
//        System.out.println("userName=" + client.getUserName());
//        System.out.println("tag=" + client.getTag());
//        System.out.println("IP:\n" + client.getIp());
//        System.out.println("Date: " + client.getDate());

        //ClientDao.addClient(client);

        System.out.println(getAllKindOfAddress());
        System.out.println(getPartOfOSInfo());
    }

    private static String getAllKindOfAddress() {
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
                byte[] hwAddr = networkInterface.getHardwareAddress() == null ? new byte[0] : networkInterface
                        .getHardwareAddress();
                if (hwAddr.length > 0) {
                    ip.append(String.format("%02x", hwAddr[0]));
                }
                for (int i = 1; i < hwAddr.length; i++) {
                    String hw = String.format("%s%02x", ":", hwAddr[i]);
                    ip.append(hw);
                }

                ip.append("\n");
            }
        } catch (SocketException e) {
            //e.printStackTrace();
        }

        return ip.toString();
    }

    private static void printOSInfo() {
        Properties properties = System.getProperties();
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String val = properties.getProperty(key);
            if (val.length() > 40) {
                val = val.substring(0, 37) + "...";
            }
            System.out.println(key + "=" + val);

        }
    }

    private static String getPartOfOSInfo() {
        StringBuilder sb = new StringBuilder();
        Properties properties = System.getProperties();
        String osName = "os.name";
        sb.append(osName + "=" + properties.getProperty(osName) + "\n");

        String osVersion = "os.version";
        sb.append(osVersion + "=" + properties.getProperty(osVersion) + "\n");

        String osArch = "os.arch";
        sb.append(osArch + "=" + properties.getProperty(osArch) + "\n");

        String fileEncoding = "file.encoding";
        sb.append(fileEncoding + "=" + properties.getProperty(fileEncoding) + "\n");

        String userName = "user.name";
        sb.append(userName + "=" + properties.getProperty(userName) + "\n");

        String userHome = "user.home";
        sb.append(userHome + "=" + properties.getProperty(userHome) + "\n");

        String userLanguage = "user.language";
        sb.append(userLanguage + "=" + properties.getProperty(userLanguage) + "\n");

        return sb.toString();
    }

}
