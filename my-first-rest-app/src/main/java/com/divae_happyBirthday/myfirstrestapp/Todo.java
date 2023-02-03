package com.divae_happyBirthday.myfirstrestapp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private String description;
    private boolean isDone;

    public String getDescription() {
        return this.description;
    }
    public Integer getId() {
        return this.id;
    }

    public boolean getIsDone (){
        return this.isDone;
    }

    public void setId(Integer id){
        this.id = id;
    }
    public void  setDescription(String description){
        this.description = description;
    }
    public void setIsDone (Boolean isDone){
        this.isDone = isDone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
