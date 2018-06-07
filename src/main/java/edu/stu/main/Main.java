package edu.stu.main;


import edu.stu.bean.RandomUser;
import edu.stu.util.HttpPost2AC;
import edu.stu.util.UserControl;
import org.apache.commons.httpclient.NameValuePair;
import org.json.JSONObject;

public class Main {

    public static final String url_ipv4 = "http://1.1.1.2/ac_portal/login.php";
    public static final String url_ipv6 = "http://[1::2]/ac_portal/login.php";

    public static void main(String[] args) {

        logout();
        try {
            Thread.sleep(2000);//让程序暂停2000毫秒
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("InterruptedException: Thread sleep ERROR!");
        }
        login();

    }

    /*注销用户的历史登录信息*/
    public static void logout() {
        NameValuePair[] data = {new NameValuePair("opr", "logout"), new NameValuePair("ipv4or6", "")};//用于注销的POST表单数据
        HttpPost2AC logoutPost = new HttpPost2AC();
        logoutPost.sendPostMethod(url_ipv4, data);
        logoutPost.sendPostMethod(url_ipv6, data);
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
            JSONObject ipv4ResponseJson = new JSONObject(loginPost.sendPostMethod(url_ipv4, data));
            flag = ipv4ResponseJson.getBoolean("success");//判断是否通过AC的验证
            String msg = ipv4ResponseJson.getString("msg");
            if (flag) {
                //若此用户通过AC的验证，则继续进行ipv6的登录
                loginPost.sendPostMethod(url_ipv6, data);
                userControl.close();
            } else {
                //若此用户未通过AC的验证，则在数据库中标记该用户为无效用户
                userControl.updateUser(user.getId(), msg);
            }
            //此处最好有一个user.close()和data.close()的过程
        } while (!flag);

    }
}
