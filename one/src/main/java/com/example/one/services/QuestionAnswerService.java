package com.example.one.services;


import com.example.one.models.*;
import com.example.one.models.Character;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


@Service
public class QuestionAnswerService {

    //Hold instances of Character
    private HashMap<String, Character> characterMap = new HashMap<String, Character>();

    //Hold instances of all retrieved quotes
    private List<Quote> totalQuotes = new ArrayList<>();
    //Hold instances of 5 selected quotes to quiz
    private List<Quote> allQuotes = new ArrayList<>();

    //for questions to list in the UI
    private List<Question> allQuestions = new ArrayList<>();

    public List<Question> getAllQuestions() {
        return allQuestions;
    }

    //for character choices to list in the UI
    private List<String> allChoices = new ArrayList<>();

    public List<String> getAllChoices() {
        return allChoices;
    }


    public void fetchCharacters() throws IOException, InterruptedException, JSONException {

        //endpoint used to retrieve characters
        String CHARACTER_URL = "https://the-one-api.dev/v2/character";

        //use HashMap to hold large number of characters and find them by using their ID
        HashMap<String, Character> characterMap = new HashMap<String, Character>();

        HttpClient client = HttpClient.newHttpClient();
        //assess key provided when after signing up
        String access_key = "lKUdzwODTP_Ufx2KYbgU";
        //allows us to use the builder design pattern to make our HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + access_key)
                .uri(URI.create(CHARACTER_URL))
                .build();
        //get a response by sending the client what was requested
        //a synchronous send blocks a process until the operation completes vs non-blocking
        //BodyHandlers gives you the response as a String
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        //parsing response body which contains JSON object containing docs : JSON array
        //into new JSON Array we can loop through
        JSONObject root = new JSONObject(httpResponse.body());
        JSONArray characters = root.getJSONArray("docs");

        //System.out.println(characters);

        //looping through JSON array of JSON objects to get the attributes to map to a Character object which
        //is then added to a map and save this information
        for (Object character : characters) {
            JSONObject slide = (JSONObject) character;
            Character characterItem = new Character();

            //populate Character here
            characterItem.set_id((String) slide.get("_id"));
            characterItem.setName((String) slide.get("name"));
            characterMap.put(characterItem.get_id(), characterItem);
        }
        this.characterMap = characterMap;
    }

    public void fetchQuotes() throws IOException, InterruptedException, JSONException {
        //retrieve all quotes from endpoint
        String QUOTE_URL = "https://the-one-api.dev/v2/quote";

        //for concurrency issues when multiple persons are accessing this service,
        // create list here and populate allChapterNames List afterwards with newChapterNames
        List<Quote> newQuotes = new ArrayList<>(); //holds total quotes
        List<Quote> selectedQuotes = new ArrayList<>(); //holds selected quotes

        HttpClient client = HttpClient.newHttpClient();
        //assess key provided when after signing up
        String access_key = "lKUdzwODTP_Ufx2KYbgU";
        //allows us to use the builder design pattern to make our HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + access_key)
                .uri(URI.create(QUOTE_URL))
                .build();
        //get a response by sending the client what was requested
        //a synchronous send blocks a process until the operation completes vs non-blocking
        //BodyHandlers gives you the response as a String
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        //System.out.println(httpResponse.body());

        //parsing response body which contains JSON object containing docs : JSON array
        //into new JSON Array we can loop through
        JSONObject root = new JSONObject(httpResponse.body());
        JSONArray quotes = root.getJSONArray("docs");

        //System.out.println(quotes);

        //looping through JSON array of JSON objects to get the attributes to map to a ChapterNameStats object which
        //is then added to a list and save this information
        for (Object quote : quotes) {
            JSONObject jsonObject = (JSONObject) quote;
            Quote quoteItem = new Quote();
            //populate chapterNameStats here
            quoteItem.setDialog((String) jsonObject.get("dialog"));
            quoteItem.setCharacter((String) jsonObject.get("character"));
            newQuotes.add(quoteItem);
        }
        this.totalQuotes = newQuotes; //saves all retrieved quotes

        for (int i = 0; i < 5; i++) {
            int number = (int) (Math.random() * newQuotes.size()); //creates random number to choose from 1-newQuotes.size()
            selectedQuotes.add(newQuotes.get(number)); //adds 5 random quote elements for new list
        }
        this.allQuotes = selectedQuotes; //save 5 random selected quotes
    }

    //Create questions and associate their character name and quotes to them
    public void fetchQuestions() throws IOException, InterruptedException {
        //call to fetch characters
        this.fetchCharacters();
        //call to fetch 5 randomly selected quotes
        this.fetchQuotes();
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Question questionItem = new Question();
            //populate
            questionItem.setCharacterIdAnswer(this.characterMap.get(this.allQuotes.get(i).getCharacter()).get_id()); //setting ID for question object
            questionItem.setCharacterName(this.characterMap.get(this.allQuotes.get(i).getCharacter()).getName()); //setting character name for question object
            questionItem.setQuoteQuestion(this.allQuotes.get(i).getDialog()); //setting the quote
            questions.add(questionItem);
        }
        this.allQuestions = questions;
    }

    //fetches random character names to add to the dropdown list in the UI
    public void fetchChoices() {
        for (int i = 0; i < 3; i++) {
            int number = (int) (Math.random() * characterMap.size()); //creates random number to choose from 1-characterMap.size()
            allChoices.add(this.characterMap.get(this.totalQuotes.get(number).getCharacter()).getName()); //retrieves random character choice elements for new list
        }
        for (Question question : this.allQuestions) {
            allChoices.add(question.getCharacterName()); //adds the correct Character name strings to the dropdown list in the UI
        }
        this.allChoices = allChoices; //saves all characters
    }

    //calculates the score after selecting character choices and clicking save from the Score object in the UI
    public Score calculate(Score choices) {
        //parses String into an array of string character names
        String[] selectedCharacters = choices.getChoice().split(",");
        //resets score to zero
        choices.setTotal(0);
        int total = 0;
        //loops through quotes to match their character IDs with matching character names in selectedCharacters[]
        //for (Question quote : this.getAllQuestions()) {}
            for (int i = 0; i < selectedCharacters.length; i++) {
                Question quote = this.getAllQuestions().get(i);
                //use equals() to determine if string data/content are the same
                if (quote.getCharacterName().equals(selectedCharacters[i])) {
                    total++;
                }
        }
        choices.setTotal(total);
        return choices; //returns the stored total score to the score.html view
    }
}
