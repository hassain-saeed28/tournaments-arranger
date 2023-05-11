package com.swe206.group_two.backend.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swe206.group_two.backend.user.User;
import com.swe206.group_two.backend.user.UserServiceImpl;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> login(@RequestBody AuthDTO authDTO) {
        Optional<User> _user = userServiceImpl.getUserByEmail(authDTO.getEmail());
        if (_user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            User user = _user.get();
            if (user.getPasswordHash().equals(
                    Password.createPasswordHash(authDTO.getPassword()))) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
    }
}
