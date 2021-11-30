package com.example.one.models;


import java.util.Arrays;

public class Question {
    private String characterIdAnswer;
    private String characterName;
    private String quoteQuestion;

    public String getCharacterIdAnswer() {
        return characterIdAnswer;
    }

    public void setCharacterIdAnswer(String characterIdAnswer) {
        this.characterIdAnswer = characterIdAnswer;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getQuoteQuestion() {
        return quoteQuestion;
    }

    public void setQuoteQuestion(String quoteQuestion) {
        this.quoteQuestion = quoteQuestion;
    }


    @Override
    public String toString() {
        return "Question{" +
                "characterIdAnswer='" + characterIdAnswer + '\'' +
                ", characterName='" + characterName + '\'' +
                ", quoteQuestion='" + quoteQuestion + '\'' +
                '}';
    }
}
