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

import java.io.IOException;

public class Spider36Kr {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpGet get = new HttpGet("https://36kr.com/p/5254629");
        response = closeableHttpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String html = EntityUtils.toString(entity, "utf-8");
            Document document = Jsoup.parse(html);
            Element body = document.body();
            Elements p = body.select("p");
            for (Element element : p) {
                System.out.println(element);
            }
        }
    }

}
