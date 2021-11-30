package com.example.one.models;

public class Quote {
    private String dialog;
    private String character;

    public String getDialog() {
        return dialog;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "dialog='" + dialog + '\'' +
                ", character='" + character + '\'' +
                '}';
    }
}
