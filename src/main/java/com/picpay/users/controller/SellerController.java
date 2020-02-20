package com.picpay.users.controller;

import com.picpay.users.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class SellerController {


    @Autowired
    private SellerService sellerService;

    @RequestMapping(value = "/users/sellers",method = RequestMethod.POST)
    public ResponseEntity<?> Post(@Valid @RequestParam(value = "user_id") Long query , @RequestBody Map<String,Object> body){
       return sellerService.createSeller(query,body);
    }

}
