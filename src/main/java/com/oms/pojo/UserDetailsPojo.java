package com.oms.pojo;

import com.oms.models.UserRoleEntity;
import lombok.Data;

import java.util.Date;

@Data
public class UserDetailsPojo {
    private Long id;
    private String contactNumber;
    private String loginId;
    private String supervisorId;
    private String userFirstName;
    private String userLastName;
    private String userPass;
    private UserRoleEntity userRoleEntity;
    private Date beginDate;
    private Date endDate;
    private boolean activeUser;
    private Integer createdBy;
    private Integer modifiedBy;
    //    private int userLevel;
    private Date createdDate;
}





