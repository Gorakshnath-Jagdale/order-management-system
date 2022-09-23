package com.oms.zcontroller;

import com.oms.pojo.UserDetailsPojo;
import com.oms.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;

    @PostMapping(
            value = "/addUser",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> addNewUser(@RequestBody UserDetailsPojo user) {
        return ResponseEntity.ok(userManagementService.addNewUserDetails(user));
    }

    @DeleteMapping(value = "/removeUser/{userId}")
    ResponseEntity<String> deleteUser(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(userManagementService.deleteUserDetails(userId));
    }
    @GetMapping(value = "/removeUser/{userId}")
    ResponseEntity<UserDetailsPojo> getUser(@PathVariable(name = "userId") Long userId) throws Exception {
        return ResponseEntity.ok(userManagementService.getUser(userId));
    }
    @GetMapping(value = "/getAllUsers")
    ResponseEntity<List<UserDetailsPojo>> getAllUsers() {
        return ResponseEntity.ok(userManagementService.getAllUsers());
    }
    @PutMapping (
            value = "/updateUser",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateUser(@RequestBody UserDetailsPojo user) {
        return ResponseEntity.ok(userManagementService.updateUserDetails(user));
    }

}
