package com.chrisV.tasktracker.tasktracker_backend;

import jakarta.persistence.*;;

@Entity
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private boolean completed;

    public Task() {}

    public Task(String tile, boolean completed) {
        this.title = tile;
        this.completed = completed;
    }

    public Long getId() {return id;}
    public String getTiltle() {return title;}
    public boolean isCompleted() {return completed;}

    public void setId(Long id) {this.id = id;}
    public void setTitle(String title) {this.title = title;}
    public void setCompleted(boolean completed) {this.completed = completed;}
}
