package com.example.athena_droid;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "article_table")
public class Article {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private String content;

    private String location;

    private int priority;

    public Article(String title, String description, int priority, String content, String location) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.content = content;
        this.location = location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public String getContent(){
        return content;
    }

    public String getLocation(){
        return location;
    }

}
