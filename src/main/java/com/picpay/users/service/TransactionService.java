package com.picpay.users.service;

import com.picpay.users.exception.ExceptionResponse;
import com.picpay.users.models.Transaction;
import com.picpay.users.repository.TransactionRepository;
import com.picpay.users.repository.UserRepository;
import com.picpay.users.util.TransactionControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionControllerUtil transactionControllerUtil;

    public ResponseEntity<?> creteTransaction(Transaction transaction){
        if(transaction.getPayeeId().equals(transaction.getPayerId())) return ExceptionResponse.ValidationError();
        if(!userRepository.findById(transaction.getPayeeId()).isPresent()) return ExceptionResponse.UserNotFoundError();
        if(!userRepository.findById(transaction.getPayerId()).isPresent()) return ExceptionResponse.UserNotFoundError();
        int status =  transactionControllerUtil.AuthorizeTransaction(transaction);
        if(status == 200){
            transaction.setTransactionDate(new Date());
            Transaction savedTransaction =  transactionRepository.save(transaction);
            return new ResponseEntity<Transaction>(savedTransaction, HttpStatus.CREATED);
        } else if(status == 401) {
            return ExceptionResponse.UnauthorizedError();
        } else {
            return ExceptionResponse.InternalError();
        }
    }

    public ResponseEntity<?> FindById(Long transaction_id){
        if(transaction_id != null){
            Optional<Transaction> byId = transactionRepository.findById(transaction_id);
            if(byId.isPresent()){
                Transaction transaction = byId.get();
                return new ResponseEntity<>(transaction, HttpStatus.OK);
            }
            return ExceptionResponse.TransactionNotFoundError();
        }
        return ExceptionResponse.InternalError();
    }
}
