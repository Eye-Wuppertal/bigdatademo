package com.tal;
/* 
    @TODO: 演示使用httpClient实现网络爬虫
    @Author tal
*/

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

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
}
