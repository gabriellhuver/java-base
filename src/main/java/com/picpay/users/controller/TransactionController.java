package com.picpay.users.controller;


import com.picpay.users.models.Transaction;
import com.picpay.users.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TransactionController {


    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<?> Post(@Valid @RequestBody Transaction transaction){
        return transactionService.creteTransaction(transaction);
    }

    @RequestMapping(value= "/transactions/{transaction_id}", method = RequestMethod.GET)
    public ResponseEntity<?> GetById(@PathVariable("transaction_id") Long transaction_id ){
        return transactionService.FindById(transaction_id);
    }


}
