package com.oms.pojo;

import lombok.Data;

@Data
public class UserDetailsPojo {
        private Long id;
        private String contactNumber;
        private String loginId;
        private String userName;
        private String userPass;
        private boolean activeUser;
        private int userLevel;
    }





