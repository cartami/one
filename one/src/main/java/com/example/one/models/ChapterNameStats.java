package com.example.one.models;

public class ChapterNameStats {

    private String _id;
    private String book;
    private String chapterName;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    //so you can print out your object
    @Override
    public String toString() {
        return "ChapterNameStats{" +
                "_id='" + _id + '\'' +
                ", book='" + book + '\'' +
                ", chapterName='" + chapterName + '\'' +
                '}';
    }
}
