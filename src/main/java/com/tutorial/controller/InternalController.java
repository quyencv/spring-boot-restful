package com.tutorial.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.constant.UrlPathConstant;
import com.tutorial.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = UrlPathConstant.Internal.PRE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class InternalController {

    private final UserService userService;

    @PutMapping("make-admin/{username}")
    public ResponseEntity<?> makeAdmin(@PathVariable String username) {
        userService.updateUserRole(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
