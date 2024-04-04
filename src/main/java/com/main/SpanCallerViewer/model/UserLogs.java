package com.main.SpanCallerViewer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserLogs {
    @Id
    private String id;
    private String key;
    private String userId;

    public UserLogs (){}

    public UserLogs(String id, String key, String userId) {
        this.id = id;
        this.key = key;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
