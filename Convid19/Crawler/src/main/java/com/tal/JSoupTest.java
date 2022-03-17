package com.tal;
/* 
    @TODO: 演示使用JSoup实现页面解析
    @Author tal
*/

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.net.URL;

public class JSoupTest {
    @Test
    public void testGetDocument() throws Exception {
        // 演示请求连接，但一般不使用Jsoup发起请求，主要用作解析
        // Document doc = Jsoup.connect("https://www.itcast.cn/").get();
        Document doc = Jsoup.parse(new URL("https://www.itcast.cn/"), 1000);
        // System.out.println(doc);

        Element titleElement = doc.getElementsByTag("title").first();
        String title = titleElement.text();
        System.out.println(title);
    }

    @Test
    public void testGetElement() throws Exception {
        // 演示请求连接，但一般不使用Jsoup发起请求，主要用作解析
        // Document doc = Jsoup.connect("https://www.itcast.cn/").get();
        Document doc = Jsoup.parse(new URL("https://www.itcast.cn/"), 1000);
        //System.out.println(doc);

        // 根据ID获取元素getElementById
        Element element = doc.getElementById("people");
        // 根据标签获取元素getElementByTag
        Element element1 = doc.getElementsByTag("title").first();
        // 根据属性获取元素getElementByAttribute
        Elements elements = doc.getElementsByAttribute("class");
        String text = element.text();
        System.out.println(text);
        System.out.println(element1.text());
        System.out.println(elements.text());
    }
    @Test
    public void testElementOperator() throws Exception {
        Document doc = Jsoup.parse(new URL("https://www.itcast.cn/"), 1000);
        Element element = doc.getElementsByAttributeValue("class", "qimo_zx itcast_click").first();
        // 获取元素中的id
        String id = element.id();
        System.out.println(id);
        // 获取元素中的classname
        String className = element.className();
        System.out.println(className);
        // 获取元素中的属性值
        String id1 = element.attr("id");
        System.out.println(id1);
        // 获取元素中的所有属性
        String attrs = element.attributes().toString();
        System.out.println(attrs);
        // 获取元素中的文本内容
        String text = element.text();
        System.out.println(text);

    }
    @Test
    public void testSelect() throws Exception {
        Document doc = Jsoup.parse(new URL("https://www.itcast.cn/"), 1000);
        // 根据标签名获取元素
        Elements spans = doc.select("span");
        for (Element span : spans){
           // System.out.println(span);
        }

        // 根据id获取元素       #
        String text = doc.select("#people").text();
        System.out.println(text);
        // 根据class获取元素    .
        String text1 = doc.select(".qimo_zx").text();
        System.out.println(text1);
        // 根据属性获取元素     []
        String text2 = doc.select("[id]").text();
        System.out.println(text2);
        // 根据属性值获取元素
        String text3 = doc.select("[class=con2_top]").text();
        System.out.println(text3);
    }

    @Test
    public void testSelect2() throws Exception {
        Document doc = Jsoup.parse(new URL("https://www.itcast.cn/"), 1000);
        // 根据元素标签名+id组合选取元素   text#id
        String text = doc.select("div#dianshang-gf").text();
        System.out.println(text);
        // 标签名+class                text.class
        // 标签名+元素名                text[name]
        // 任意组合                    text[name].class
        // 查找某个元素下的直接子元素     .class > xx > xx
        String text1 = doc.select(".evaluation_con > div > p").text();
        System.out.println(text1);
        // 查找某个元素下的所有子元素      .class xx  或者  .class > *

    }
}
