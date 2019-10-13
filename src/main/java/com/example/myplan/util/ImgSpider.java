package com.example.myplan.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;


public class ImgSpider {

    public static void main(String[] args) throws IOException {
        List<String> list = new ArrayList<>();
        firstStep("http://www.bbsnet.com");
    }

    public static void firstStep(String url) throws IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpGet get = new HttpGet(url);
        response = closeableHttpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String html = EntityUtils.toString(entity, "utf-8");
            Document document = Jsoup.parse(html);
            Element body = document.body();
            body.select("script").remove();

            System.out.println("==================================");
            Elements allElements = body.getAllElements();

            Elements a = allElements.select("a");

            Set<String> spiderUrlList = new HashSet<>();
            Set<String> theSecongUrlSet = new HashSet<>();
            for (Element allElement : a) {
                String href = allElement.attr("href");
                if (href.contains(".html")){
                    spiderUrlList.add(href);

                    System.out.println(href);
                }else if (href.contains("http://")
                        && !(href.contains("vip")
                        && !href.contains("login")
                        && !href.contains("denglu")
                        && !href.contains("fuwu"))){
                    theSecongUrlSet.add(href);
                }
            }
            //spiderUrlList中存的为可以直接下载的url
            for (String s : spiderUrlList) {
                spider(s);
            }

            theSecongUrlSet.remove("http://www.miitbeian.gov.cn/");
            theSecongUrlSet.remove("http://www.bbsnet.com/wp-login.php?redirect_to=http%3A%2F%2Fwww.bbsnet.com%2F");
            theSecongUrlSet.remove("http://www.bbsnet.com/");
            theSecongUrlSet.remove("http://www.wordpress.org/");
            theSecongUrlSet.remove("http://www.bbsnet.com/tag");
            theSecongUrlSet.remove("http://weibo.com/doutupian");
            theSecongUrlSet.remove("http://www.bbsnet.com/fuwu");
            for (String s : theSecongUrlSet) {
                System.out.println(s);
                firstStep(s);
            }
        }
    }


    /**
     * 执行下载的方法
     * @param url
     * @throws IOException
     */
    public static void spider(String url) throws IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        HttpGet get = new HttpGet(url);
        response = closeableHttpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String html = EntityUtils.toString(entity, "utf-8");
            Document document = Jsoup.parse(html);
            Element body = document.body();
            body.select("script").remove();

            System.out.println("==================================");
            Elements elements = body.select("p");
            Elements img = elements.select("img");
            List<Element> collect = img.stream().filter(a -> !a.attr("alt").equals("支付宝") && !a.attr("alt").equals("微信支付")).collect(Collectors.toList());

            for (Element element : collect) {
                System.out.println(element);
                String src = element.attr("src");
                String title = element.attr("title");
                System.out.println(title);
                try {
                    download(src,title+""+System.currentTimeMillis()+".gif","E:\\myimage\\"+title+"\\");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void download(String urlString, String filename,String savePath) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(5*1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf=new File(savePath);
        if(!sf.exists()){
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }

}