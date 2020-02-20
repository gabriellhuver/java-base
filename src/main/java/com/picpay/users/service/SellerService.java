package com.picpay.users.service;

import com.picpay.users.exception.ExceptionResponse;
import com.picpay.users.models.Seller;
import com.picpay.users.models.User;
import com.picpay.users.repository.SellerRepository;
import com.picpay.users.repository.UserRepository;
import com.picpay.users.util.UserControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class SellerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    UserControllerUtil userControllerUtil;

    public ResponseEntity<?> createSeller(Long query, Map<String,Object> body){
        String username = (String) body.get("username");
        if(query == null){ return ExceptionResponse.UserNotFoundError(); }
        Optional<User> user =  userRepository.findById(query);
        if(user.isPresent()){
            if(!userControllerUtil.IsValidUsername(username)){return ExceptionResponse.ValidationError();}
            try {
                User userToUpdate = user.get();
                Seller saved = sellerRepository.save(userControllerUtil.MapBodyToSeller(body,query));
                userToUpdate.setSeller(saved);
                userRepository.save(userToUpdate);
                return new ResponseEntity<Seller>(saved, HttpStatus.CREATED);
            } catch (Exception e){
                return ExceptionResponse.ValidationError();
            }
        } else {
            return ExceptionResponse.UserNotFoundError();
        }

    }
}
