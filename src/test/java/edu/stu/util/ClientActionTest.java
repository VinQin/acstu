package edu.stu.util;

import edu.stu.bean.ClientInfo;


public class ClientActionTest {


    public static void main(String[] args) {
        ClientInfo client = new ClientAction().getClientInfo("16fguo", true);
//        System.out.println("userName=" + client.getUserName());
//        System.out.println("tag=" + client.getTag());
//        System.out.println("IP:\n" + client.getIp());
//        System.out.println("Date: " + client.getDate());

        ClientDao.addClient(client);
    }

}
