package com.tutorial.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.constant.UrlPathConstant;
import com.tutorial.dto.UserDTO;
import com.tutorial.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = UrlPathConstant.Authentication.PRE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping(UrlPathConstant.Authentication.SIGN_UP)
    public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO) throws Exception {
        if (userService.existsByUsername(userDTO.getUsername())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.save(userDTO), HttpStatus.CREATED);
    }
}
