package com.oms.zcontroller;

import com.oms.pojo.UserDetailsPojo;
import com.oms.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;

    //@GetMapping(value = "/addUser")
    @PostMapping(
            value = "/addUser",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> addNewUser(@RequestBody UserDetailsPojo user) {
        return ResponseEntity.ok(userManagementService.addNewUserDetails(user));
    }
//Perform following operation in HTTP delete
    @DeleteMapping(value = "/removeuser/{userId}")
    ResponseEntity<String> deleteUser(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(userManagementService.deleteUserDetails(userId));
    }

    @PutMapping (
            value = "/updateUser",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> updateUser(@RequestBody UserDetailsPojo user) {
        return ResponseEntity.ok(userManagementService.updateUserDetails(user));
    }

}
