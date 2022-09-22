package com.oms.zcontroller;

import com.oms.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;

    @PostMapping(value = "/addUser")
    ResponseEntity<String> addNewUser() {
        return ResponseEntity.ok(userManagementService.addNewUserDetails());
    }

    @DeleteMapping(value = "/removeUser")
    ResponseEntity<String> deleteUser() {
        return ResponseEntity.ok(userManagementService.deleteUserDetails());
    }

    @PutMapping(value = "/updateUser")
    ResponseEntity<String> updateUser() {
        return ResponseEntity.ok(userManagementService.updateUserDetails());
    }

}
