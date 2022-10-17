package com.oms.dto;

import lombok.Data;

@Data
public class Requester
{
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;
    private int userLevel;
}