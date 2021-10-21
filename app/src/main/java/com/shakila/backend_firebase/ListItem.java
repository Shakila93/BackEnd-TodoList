package com.shakila.backend_firebase;

import com.google.firebase.Timestamp;

import java.io.Serializable;

public class ListItem implements Serializable {
    private String UserID;
    private String Name;
    private String Description;
    private Timestamp CreatedAt;
    private Timestamp CompletedAt;

    public ListItem(){
        this.UserID = "";
        this.Name = "";
        this.Description = "";
        this.CreatedAt = null;
        this.CompletedAt = null;
    }
    public ListItem(String userID, String name, String description, Timestamp createdAt, Timestamp completedAt) {
        this.UserID = userID;
        this.Name = name;
        this.Description = description;
        this.CreatedAt = createdAt;
        this.CompletedAt = completedAt;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        this.UserID = userID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public Timestamp getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.CreatedAt = createdAt;
    }

    public Timestamp getCompletedAt() {
        return CompletedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.CompletedAt = completedAt;
    }
}
