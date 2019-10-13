package com.example.myplan.util;


import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MySpider {


    public static void main(String[] args) throws IOException {
        //输入要爬取的页面
        String url = "https://mp.weixin.qq.com/s/UKwZkwYmDSVTbj62ACuapg";
//        String url = "https://www.cnblogs.com/sam-uncle/p/10908567.html";
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        HttpGet get = new HttpGet(url);
        response = closeableHttpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            HttpEntity entity = response.getEntity();
            String html = EntityUtils.toString(entity, "utf-8");
            Document document = Jsoup.parse(html);
            Element body = document.body();
            body.select("script").remove();
//            System.out.println("************截取只有文章正文的部门Element");
            Element article = body.getElementById("js_article");
//            System.out.println(article);

            System.out.println("==================================");
            Elements elements = body.getAllElements();

            //获取要进行筛选的正文
            List<Element> collect = elements.stream().filter(a -> a.getElementsByAttribute("mpa-is-content").size() != 0).collect(Collectors.toList());
            Element theTrueArt = collect.get(0);

            Elements p = theTrueArt.select("p");
            List<Element> style = p.stream().filter(a -> a.attr("style").equals("-webkit-print-color-adjust: exact;margin-top: 15px;margin-bottom: 15px;font-family: Helvetica, arial, sans-serif;font-size: 14px;white-space: normal;background-color: rgb(255, 255, 255);")).collect(Collectors.toList());

            long l = System.currentTimeMillis();
            for (Element element : style) {
                System.out.println(element);
                String pathname = "E:\\study\\p\\" + element.text();
                File file = new File(pathname);
                file.mkdirs();
            }
            System.out.println("------------分割线-------------");
              Elements h4 = theTrueArt.select("h4");
            List<Element> style1 = h4.stream().filter(a -> a.attr("style").equals("-webkit-print-color-adjust: exact;margin-top: 20px;margin-bottom: 10px;font-weight: bold;-webkit-font-smoothing: antialiased;cursor: text;font-family: Helvetica, arial, sans-serif;white-space: normal;background-color: rgb(255, 255, 255);")).collect(Collectors.toList());

            for (Element element : style1) {
                System.out.println(element);
                String pathname = "E:\\study\\h4\\" + element.text();
                File file = new File(pathname);
                file.mkdirs();
            }
            System.out.println("====耗时:"+(System.currentTimeMillis()-l)+"");
            Elements t = theTrueArt.getElementsByAttributeValue("mpa-is-content", "t");
            Map<String,String> map = new LinkedHashMap();
            List<String> list = new ArrayList();
            int b = 0;
            for (int i = 0; i < t.size(); i++) {
                if (t.get(i).hasAttr("style")){
                    list.add(t.get(i).text());
                    b++;
                }else {
                    map.put(t.get(i).text(),list.get(b-1));
                }
            }


//            for (String o : map.keySet()) {
//                String pathname = "E:\\study\\" +map.get(o) + "\\" + o;
//                File file = new File(pathname);
//                file.mkdirs();
//            }









        }
        //6.关闭
        HttpClientUtils.closeQuietly(response);
        HttpClientUtils.closeQuietly(closeableHttpClient);




    }
}