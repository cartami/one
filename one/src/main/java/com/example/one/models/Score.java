package com.example.one.models;

import java.util.List;

public class Score {

    private String choice;
    private int total;
    private List<String> choices;


    public void addChoice(String choice) {
        this.choices.add(choice);
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Score{" +
                "choice='" + choice + '\'' +
                ", total=" + total +
                ", choices=" + choices +
                '}';
    }
}
