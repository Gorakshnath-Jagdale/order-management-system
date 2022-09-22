package com.oms.zcontroller;

import com.oms.service.UserManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserManagementController {

    private final UserManagementService userManagementService;

    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    //@GetMapping(value = "/test")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    ResponseEntity<String> addNewUser()
    {
        userManagementService.addNewUserDetails();
       return ResponseEntity.ok("WOOW- IT WORKED");
    }
}
