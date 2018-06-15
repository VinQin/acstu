package edu.stu.main;


import edu.stu.bean.ClientInfo;
import edu.stu.bean.RandomUser;
import edu.stu.util.ClientAction;
import edu.stu.util.ClientDao;
import edu.stu.util.HttpPost2AC;
import edu.stu.util.UserControl;
import org.apache.commons.httpclient.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static final String url_ipv4 = "http://1.1.1.2/ac_portal/login.php";
    public static final String url_ipv6 = "http://[1::2]/ac_portal/login.php";

    public static void main(String[] args) {

        logoutPlus();
        try {
            Thread.sleep(2000);//让程序暂停2000毫秒
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("InterruptedException: Thread sleep ERROR!");
        }
        loginPlus();


    }

    /*注销用户的历史登录信息*/
    public static void logout() {
        NameValuePair[] data = {new NameValuePair("opr", "logout"), new NameValuePair("ipv4or6", "")};//用于注销的POST表单数据
        HttpPost2AC logoutPost = new HttpPost2AC();
        logoutPost.setUrl(url_ipv4);
        logoutPost.sendPostMethod(data);

        logoutPost.setUrl(url_ipv6);
        logoutPost.sendPostMethod(data);
    }

    /*有效用户的登录*/
    public static void login() {
        HttpPost2AC loginPost = new HttpPost2AC();
        UserControl userControl = new UserControl();
        boolean flag = false;

        do {
            RandomUser user = userControl.getRandomUser();//从数据库中获取一个有效的随机用户
            NameValuePair[] data = {new NameValuePair("opr", "pwdLogin"), new NameValuePair("userName", user
                    .getUsername()), new NameValuePair("pwd", user.getPassword()), new NameValuePair("ipv4or6",
                    "")
                    , new NameValuePair("rememberPwd", "0")};//用于登录的POST表单数据
            loginPost.setUrl(url_ipv4);
            JSONObject ipv4ResponseJson = new JSONObject(loginPost.sendPostMethod(data));
            flag = ipv4ResponseJson.getBoolean("success");//判断是否通过AC的验证
            String msg = ipv4ResponseJson.getString("msg");
            if (flag) {
                //若此用户通过AC的验证，则继续进行ipv6的登录
                loginPost.setUrl(url_ipv6);
                loginPost.sendPostMethod(data);
                userControl.close();
            } else {
                //若此用户未通过AC的验证，则在数据库中标记该用户为无效用户
                userControl.updateUser(user.getId(), msg);
            }
            //此处最好有一个user.close()和data.close()的过程
        } while (!flag);

    }

    //使用org.apache.httpcomponents进行登出
    public static void logoutPlus() {
        Map<String, String> param = new HashMap<>();
        param.put("opr", "logout");
        param.put("ipv4or6", "");
        HttpPost2AC logout = new HttpPost2AC();
        logout.setUrl(url_ipv4);
        logout.requestForHttp(param);

        logout.setUrl(url_ipv6);
        logout.requestForHttp(param);
    }

    //使用org.apache.httpcomponents进行登录
    public static void loginPlus() {
        ClientInfo client = new ClientAction().getClientInfo("somebody", true);

        UserControl userControl = new UserControl();
        HttpPost2AC login = new HttpPost2AC();
        Map<String, String> param = new HashMap<>();
        param.put("opr", "pwdLogin");
        param.put("ipv4or6", "");
        param.put("rememberPwd", "0");

        boolean flag = false;
        do {
            RandomUser user = userControl.getRandomUser();
            if (user == null) {
                System.out.println("登录尝试失败！");
                break;
            }
            param.put("userName", user.getUsername());

            client.setUserName(user.getUsername());//记录用户所使用的登录账户
            client.setDate(new Date());//记录用户本次登录时间

            param.put("pwd", user.getPassword());
            login.setUrl(url_ipv4);
            String jsonResult = login.requestForHttp(param);
            JSONObject ipv4ResponseJson = new JSONObject(jsonResult);
            String msg = "";
            try {
                flag = ipv4ResponseJson.getBoolean("success");//判断是否通过AC的验证
                msg = ipv4ResponseJson.getString("msg");
            } catch (JSONException e) {
                System.out.println("登录尝试失败！");
                e.printStackTrace();
                return;
            }

            if (flag) {
                //若此用户通过AC的验证，则继续进行ipv6的登录
                login.setUrl(url_ipv6);
                login.requestForHttp(param);
                userControl.close();

                client.setTag(true);//标记用户所使用的账户登录有效
                client.setDate(new Date());//更新一下时间
                ClientDao.addClient(client);//往数据库中添加一条用户登录行为的记录

            } else {
                //若此用户未通过AC的验证，则在数据库中标记该用户为无效用户
                userControl.updateUser(user.getId(), msg);
                System.out.println("重新尝试中...");
                param.remove("userName");
                param.remove("pwd");

                client.setTag(false);//标记用户所使用的账户登录无效
                client.setDate(new Date());//更新一下时间
                ClientDao.addClient(client);//往数据库中添加一条用户登录行为的记录
            }


        } while (!flag);

    }

}
