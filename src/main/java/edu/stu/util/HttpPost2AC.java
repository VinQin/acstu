package edu.stu.util;

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpPost2AC {

    // 向长春理工大学校园网AC发送HTTP POST请求
    public String sendPostMethod(String url, NameValuePair[] data) {
        String responseJson = ""; // 要返回的JSON信息
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);

        postMethod.setRequestBody(data); // 将表单的值放入HTTP的post方法中
        int statusCode = 0; // 从AC中获得的返回状态
        try {
            statusCode = httpClient.executeMethod(postMethod);
        } catch (HttpException e) {
            e.printStackTrace();
            System.out.println("HttpException: Send HTTP POST FORM FAILURE!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException: Send HTTP POST FORM FAILURE!");
        }

        try {
            responseJson = postMethod.getResponseBodyAsString();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException: Get HTTP POST Response ERROR!");
        }
        postMethod.releaseConnection(); // 释放连接资源

        return responseJson;
    }
}
