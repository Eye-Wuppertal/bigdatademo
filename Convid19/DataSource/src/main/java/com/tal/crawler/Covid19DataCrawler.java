package com.tal.crawler;
/* 
    @TODO: 实现疫情数据爬取
    @Author tal
*/

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tal.bean.CovidBean;
import com.tal.utils.HttpUtils;
import com.tal.utils.TimeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
@Component
public class Covid19DataCrawler {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    // 后续将该方法改为定时任务,定时爬取数据
    //@Scheduled(cron = "0 0 8 * * ?") // 每天八点执行
    @Scheduled(cron = "0/5 * * * * ?") // 每5s执行一次，用于测试
    public void testCrawling() throws Exception {
        String datetime = TimeUtils.format(System.currentTimeMillis(), "yyyy-MM-dd");
        // 1、爬取指定页面
        String html = HttpUtils.getHtml("https://ncov.dxy.cn/ncovh5/view/pneumonia");
        // System.out.println(html);

        // 2、解析页面中的指定内容
        Document doc = Jsoup.parse(html);
        String text = doc.select("script[id = getAreaStat]").toString();
        //System.out.println(text);

        // 3、使用正则表达式获取疫情json格式的数据
        String pattern = "\\[(.*)\\]";              //  定义正则规则
        Pattern reg = Pattern.compile(pattern);     //  编译成正则对象
        Matcher matcher = reg.matcher(text);        //  去text中进行匹配
        String jsonStr = "";
        if (matcher.find()){
            jsonStr = matcher.group(0);
            //System.out.println(jsonStr);
        }else {
            System.out.println("No Match");
        }

        // 对json数据进行进一步的解析
        // 4、将第一层的json（省份数据）解析为JavaBean
        List<CovidBean> pCovidBeans = JSON.parseArray(jsonStr, CovidBean.class);
        for(CovidBean pBean : pCovidBeans){ //pBean 为省份
            //System.out.println(pBean);
            // 先设置时间字段
            pBean.setDatetime(datetime);
            // 获取cities
            String citiesStr = pBean.getCities();
            // 5、将第二层json（城市数据）解析为JavaBean
            List<CovidBean> covidBeans = JSON.parseArray(citiesStr, CovidBean.class);
            for(CovidBean bean : covidBeans){   //bean 为城市
                //System.out.println(bean);
                bean.setDatetime(datetime);
                bean.setPid(pBean.getLocationId());
                bean.setProvinceShortName(pBean.getProvinceShortName());
                //System.out.println(bean);
                // 后续需要将城市疫情数据发送给kafka
                // 将JavaBean转化为jsonStr再发送给kafka
                String beanStr = JSON.toJSONString(bean);
                kafkaTemplate.send("covid19",bean.getPid(),beanStr);

            }
            // 6、获取第一层json（省份数据）中的每一天的统计数据
            String statisticsDataUrl = pBean.getStatisticsData();
            String statisticsDataStr = HttpUtils.getHtml(statisticsDataUrl);
            // 获取statisticsDataStr中data对应的数据
            JSONObject jsonObject = JSON.parseObject(statisticsDataStr);
            String dataStr = jsonObject.getString("data");
            //System.out.println(dataStr);
            // 将爬取解析出来的数据设置回pBean中的statisticsData,替换原先的url
            pBean.setStatisticsData(dataStr);
            pBean.setCities(null);
            //System.out.println(pBean);
            // 后续需要将省份疫情数据发送给kafka
            String pBeanStr = JSON.toJSONString(pBean);
            kafkaTemplate.send("covid19",pBean.getLocationId(),pBeanStr);
        }
    }

}
