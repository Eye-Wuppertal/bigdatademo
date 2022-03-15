package com.tal;
/* 
    @TODO: 演示使用JDK自带的API实现网络爬虫
    @Author tal
*/

import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;


public class JDKAPITest {
    @Test
    public void testGet() throws Exception {
        // 1. 确定要访问、爬取的URL
        URL url = new URL("https://github.com/Eye-Wuppertal/");

        // 2. 获取连接对象
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); // 指定http连接

        // 3. 设置连接信息：请求方式、请求参数、请求头等
        httpURLConnection.setRequestMethod("GET");   // 请求方式默认就是GET，必须为大写
        // 设置属性Property 参数 "key:  ","value:  "
        httpURLConnection.setRequestProperty("User-Agent","indows NT 10.Mozilla/5.0 (W0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");
        httpURLConnection.setConnectTimeout(30000);  // 设置超时时间，单位毫秒

        // 4. 获取数据
        InputStream inputStream = httpURLConnection.getInputStream();   // 获取响应回来的数据流
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));  //将获取的数据封装成BufferReader一行一行读出来
        String line;
        String html = "";
        while((line = reader.readLine()) != null ){
            html += line + "\n";
        }
        System.out.println(html.toString());

        // 5. 关闭资源
        inputStream.close();
        reader.close();
    }
    @Test
    public void testPOST() throws Exception {
        // 1. 确定要访问、爬取的URL
        URL url = new URL("https://www.itcast.cn/");

        // 2. 获取连接对象
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); // 指定http连接

        // 3. 设置连接信息：请求方式、请求参数、请求头等
        httpURLConnection.setDoOutput(true);    // 允许向URL输出内容
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");
        httpURLConnection.setConnectTimeout(30000);  // 设置超时时间，单位毫秒
        OutputStream outputStream = httpURLConnection.getOutputStream();  // 默认不让用，需要第一步的setDoOutput
        outputStream.write("username=xx".getBytes());

        // 4. 获取数据
        InputStream inputStream = httpURLConnection.getInputStream();   // 获取响应回来的数据流
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));  //将获取的数据封装成BufferReader一行一行读出来
        String line;
        String html = "";
        while((line = reader.readLine()) != null ){
            html += line + "\n";
        }
        System.out.println(html.toString());

        // 5. 关闭资源
        inputStream.close();
        reader.close();
    }
}
