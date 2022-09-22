package com.oms.service;

import com.oms.models.UserDetailsEntity;
import com.oms.models.repository.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserDetailsRepository userDetailsRepository;

    public String addNewUserDetails() {
        var newUser = new UserDetailsEntity();
        newUser.setUserName("OMS");
        newUser.setUserPass("OMS");
        newUser.setLoginId("OMS");
        newUser.setContactNumber("939803163");
        newUser.setActiveUser(true);
        var savedUser = userDetailsRepository.save(newUser);
        return savedUser.getUserName() + " Save with Login Id -" + savedUser.getLoginId() + " User Id generated is :" + savedUser.getId();
    }

    public String deleteUserDetails() {

        return null;
    }

    public String updateUserDetails() {
        return null;
    }
}
