package com.oms.dto;

import lombok.Data;

@Data
public class Requester {
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    //private int userLevel;
}