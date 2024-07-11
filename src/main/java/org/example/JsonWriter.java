package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonWriter {

    private ObjectMapper objectMapper = new ObjectMapper();

    private final File file = new File("news.scrap");

    public void write(Set<News> newsSet) {

        Map<String,String> newsMap = new HashMap<>();

        try {
            // 파일이 존재하면 기존 데이터를 읽어오기
            if (file.exists()) {
                newsMap = objectMapper.readValue(file, new TypeReference<Map<String, String>>() {
                });
            }

            for (News news : newsSet) {
                newsMap.put(news.url(),news.text());
            }

            objectMapper.writeValue(file, newsMap);

        } catch (IOException e) {
            e.printStackTrace();
        }


        for (News news : newsSet) {
            newsMap.put(news.url(),news.text());
        }

    }
}
