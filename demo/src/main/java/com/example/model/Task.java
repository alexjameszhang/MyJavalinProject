package com.example.model;

public class Task {
    private int id;
    private String title;
    private int[] date = new int[3];

    public Task() {}

    public Task(int id, String title, int[] date) {
        this.id = id;
        this.title = title;
        this.date = date; 
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int[] getDate() { return date; }

}