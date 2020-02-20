package com.picpay.users.controller;


import com.picpay.users.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @RequestMapping(value = "/users/consumers",method = RequestMethod.POST)
    public ResponseEntity<?> Post(@Valid @RequestBody Map<String,Object> body){
        return consumerService.createConsumer(body);
    }


}
