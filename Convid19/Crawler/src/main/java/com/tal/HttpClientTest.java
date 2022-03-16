package com.tal;
/* 
    @TODO: 演示使用httpClient实现网络爬虫
    @Author tal
*/

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class HttpClientTest {
    @Test
    public void testGet() throws Exception {
        // 1. 创建HttpClient对象
        // DefaultHttpClient defaultHttpClient = new DefaultHttpClient(); // 已失效
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 2. 创建HttpClient请求并进行相关设置
        HttpGet httpGet = new HttpGet("https://github.com/search?q=res");
        httpGet.setHeader("User-Agent","indows NT 10.Mozilla/5.0 (W0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");

        // 3. 发起请求
        CloseableHttpResponse response = httpClient.execute(httpGet);

        // 4. 判断响应状态并获取相应数据
        if(response.getStatusLine().getStatusCode() == 200){  // 200表示响应成功
            String html = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(html);

        }

        // 5. 关闭资源
        httpClient.close();
        response.close();
    }

    @Test
    public void testPost() throws IOException {
        // 1. 创建HttpClient对象
        // DefaultHttpClient defaultHttpClient = new DefaultHttpClient(); // 已失效
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 2. 创建HttpClient请求并进行相关设置
        HttpPost httpPost = new HttpPost("https://www.itcast.cn/");
        // 准备集合存放请求参数
        ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username","java"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
        httpPost.setEntity(entity);     // 设置请求体/参数
        httpPost.setHeader("User-Agent","indows NT 10.Mozilla/5.0 (W0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");

        // 3. 发起请求
        CloseableHttpResponse response = httpClient.execute(httpPost);

        // 4. 判断响应状态并获取相应数据
        if(response.getStatusLine().getStatusCode() == 200){  // 200表示响应成功
            String html = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(html);
        }

        // 5. 关闭资源
        httpClient.close();
        response.close();
    }

    @Test
    // 测试httpClient连接池
    public void testPool() throws IOException {
        // 1、创建HttpClient 连接管理器
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

        // 2、设置参数
        cm.setMaxTotal(200);            // 设置最大连接数
        cm.setDefaultMaxPerRoute(20);   // 设置主机的最大并发
        doGet(cm);
        doGet(cm);
    }

    private void doGet(PoolingHttpClientConnectionManager cm) throws IOException {
        // 3、从连接池中获取httpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        // 断点观察到，每次从池中获取到一个HttpClient对象

        // 4、创建HttpGet对象
        HttpGet httpGet = new HttpGet("https://www.itcast.cn/");

        // 5、发送请求
        CloseableHttpResponse response = httpClient.execute(httpGet);;

        // 6、获取数据
        if(response.getStatusLine().getStatusCode() == 200){  // 200表示响应成功
            String html = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(html);
        }

        // 7、关闭资源
        // httpClient.close(); 不用关闭httpClient对象，因为使用连接池，HttpClient对象使用完后要回到连接池中，而不是关掉
        response.close();

    }

}
