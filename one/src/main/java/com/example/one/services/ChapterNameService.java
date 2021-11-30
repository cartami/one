package com.example.one.services;


import com.example.one.models.ChapterNameStats;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


//marks this as a Spring Service
@Service
public class ChapterNameService {

    //Hold instances of ChapterNameStats
    private List<ChapterNameStats> allChapterNames = new ArrayList<>();
    //use this to expose chapterNames
    public List<ChapterNameStats> getAllChapterNames() {
        return allChapterNames;
    }


    public void fetchOneData(String chapterName) throws IOException, InterruptedException, JSONException {
        //System.out.println(java.net.URLEncoder.encode(chapterName, "UTF-8").replace("+", "%20"));

        //converting a String to the application/x-www-form-urlencoded MIME format.
        String ONE_DATA_URL = "https://the-one-api.dev/v2/chapter?chapterName=" + java.net.URLEncoder.encode(chapterName, "UTF-8").replace("+", "%20");
        //for concurrency issues when multiple persons are accessing this service,
        // create list here and populate allChapterNames List afterwards with newChapterNames
        List<ChapterNameStats> newChapterNames = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        //assess key provided when after signing up
        String access_key = "lKUdzwODTP_Ufx2KYbgU";
        //allows us to use the builder design pattern to make our HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + access_key)
                .uri(URI.create(ONE_DATA_URL))
                .build();
        //get a response by sending the client what was requested
        //a synchronous send blocks a process until the operation completes vs non-blocking
        //BodyHandlers gives you the response as a String
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        //parsing response body which contains JSON object containing docs : JSON array
        //into new JSON Array we can loop through
        JSONObject root = new JSONObject(httpResponse.body());
        JSONArray chapters = root.getJSONArray("docs");

        //System.out.println(chapters);

        //looping through JSON array of JSON objects to get the attributes to map to a ChapterNameStats object which
        //is then added to a list and save this information
        for (Object chapter : chapters) {
            JSONObject jsonObject = (JSONObject) chapter;
            ChapterNameStats chapterNameStats = new ChapterNameStats();
            //populate chapterNameStats here
            chapterNameStats.setChapterName((String) jsonObject.get("chapterName"));
            chapterNameStats.setBook((String) jsonObject.get("book"));
            chapterNameStats.set_id((String) jsonObject.get("_id"));
            newChapterNames.add(chapterNameStats);
        }
        this.allChapterNames = newChapterNames;

    }

}
