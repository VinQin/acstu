package edu.stu.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpPost2AC {

    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    // 向长春理工大学校园网AC发送HTTP POST请求
    public String sendPostMethod(NameValuePair[] data) {
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

    //向STU的AC发送POST
    public String requestForHttp(Map<String, String> param) {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        List<BasicNameValuePair> params = new ArrayList<>();
        Iterator<Map.Entry<String, String>> it = param.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> en = it.next();
            String key = en.getKey();
            String value = en.getValue();
            if (value != null) {
                params.add(new BasicNameValuePair(key, value));
            }
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            /*HttpResponse*/
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, "utf-8");

            EntityUtils.consume(httpEntity);//释放资源
            httpClient.close();
            httpResponse.close();
        } catch (IOException e) {

        }

        return result;

    }
}
