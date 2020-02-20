package com.picpay.users.controller;

import com.picpay.users.models.User;
import com.picpay.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<?> Post(@Valid @RequestBody User userBody) {
       return userService.createUser(userBody);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<?> Get(@RequestParam(value = "q", required = false) String query) {
        return userService.FindUser(query);
    }

    @RequestMapping(value= "/users/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<?> GetById(@PathVariable("user_id") Long user_id ){
        return userService.findById(user_id);
    }


}
