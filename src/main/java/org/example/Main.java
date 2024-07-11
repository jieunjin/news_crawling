package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;

public class Main {
    public static void main(String[] args) {

        Set<News> newsSet = new HashSet<>();

        // 1) 크롤링 링크 [ 네이버 뉴스 - IT ]
        String URL = "https://news.naver.com/section/104";

        try {
            // 2) URL에 해당하는 document 가져오기
            Document document = Jsoup.connect(URL).get();

            // 3) sa_list로 시작하는 ul 태그 첫번째 값 가져오기
            Element ulTag = document.getElementsByClass("sa_list").get(0);

            List<Element> liList = ulTag.getElementsByClass("sa_item");

            for (Element el : liList) {
//                System.out.println(el.getAllElements());
                // 4) sa_text 라는 div 태그 추출
                Element saText = el.getElementsByClass("sa_text").get(0);

                // 5) div태그에서 href link 추출
                Element urlTitle = saText.getElementsByClass("sa_text_title").get(0);
                String newsUrl = urlTitle.attr("href");

                // 6) div태그에서 news text 추출
                Element urlText = saText.getElementsByClass("sa_text_strong").get(0);
                String newsText = urlText.text();

                System.out.println("title : " + newsText + ", url : " + newsUrl);

                News news = new News(newsUrl, newsText);

                newsSet.add(news);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //파일 저장

        JsonWriter jsonWriter = new JsonWriter();

        jsonWriter.write(newsSet);
    }
}
