package com.oms.service;

import com.oms.models.UserDetailsEntity;
import com.oms.models.repository.UserDetailsRepository;
import com.oms.pojo.UserDetailsPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserDetailsRepository userDetailsRepository;

    public String addNewUserDetails(UserDetailsPojo user) {
        var newUser = new UserDetailsEntity();
        newUser.setUserName(user.getUserName());
        newUser.setUserPass(user.getUserPass());
        newUser.setLoginId(user.getLoginId());
        newUser.setContactNumber(user.getContactNumber());
        newUser.setActiveUser(true);
        var savedUser = userDetailsRepository.save(newUser);
        return savedUser.getUserName() + " Save with Login Id -" + savedUser.getLoginId() + " User Id generated is :" + savedUser.getId();
    }

    public String deleteUserDetails(Long userId) {
        if (userDetailsRepository.existsById(userId)) {
            //Delete user operation
            userDetailsRepository.deleteById(userId);
            return userId + " User deleted ";

        } else {
            return "User Not Exist";
        }

    }

    public String updateUserDetails(UserDetailsPojo user) {
        if (userDetailsRepository.existsById(user.getId())) {
            var updatingUser = new UserDetailsEntity();
            updatingUser.setUserName(user.getUserName());
            updatingUser.setId(user.getId());
            updatingUser.setUserPass(user.getUserPass());
            updatingUser.setLoginId(user.getLoginId());
            updatingUser.setContactNumber(user.getContactNumber());
            updatingUser.setActiveUser(user.isActiveUser());
            var savedUser = userDetailsRepository.save(updatingUser);
            return savedUser.getUserName() + " Save with Login Id -" + savedUser.getLoginId() + " User Id generated is :" + savedUser.getId();
        } else {
            return "Does Not Exist";
        }

    }

    public List<UserDetailsPojo> getAllUsers() {
        return userDetailsRepository.findAll().stream().map(this::userDetailsMapper).collect(Collectors.toList());
    }


    public UserDetailsPojo userDetailsMapper(UserDetailsEntity userDetailsEntity) {

        var updatingUser = new UserDetailsPojo();
        updatingUser.setUserName(userDetailsEntity.getUserName());
        updatingUser.setId(userDetailsEntity.getId());
        updatingUser.setUserPass(userDetailsEntity.getUserPass());
        updatingUser.setLoginId(userDetailsEntity.getLoginId());
        updatingUser.setContactNumber(userDetailsEntity.getContactNumber());
        updatingUser.setActiveUser(userDetailsEntity.isActiveUser());
        return updatingUser;
    }

    public UserDetailsPojo getUser(Long userId) throws Exception {
        var user = userDetailsRepository.findById(userId).orElseThrow(Exception::new);
        return userDetailsMapper(user);
    }
}
