package com.picpay.users.service;

import com.picpay.users.exception.ExceptionResponse;
import com.picpay.users.models.Consumer;
import com.picpay.users.models.User;
import com.picpay.users.repository.ConsumerRepository;
import com.picpay.users.repository.UserRepository;
import com.picpay.users.util.UserControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class ConsumerService {

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserControllerUtil userControllerUtil;



    public ResponseEntity<?> createConsumer(Map<String,Object> body){
        String username = (String) body.get("username");
        Optional<User> user =  userRepository.findById(Long.valueOf((Integer) body.get("user_id")));
        if(user.isPresent()){
            if(!userControllerUtil.IsValidUsername(username)){return ExceptionResponse.ValidationError();}
            try {
                User userToUpdate = user.get();
                Consumer saved = consumerRepository.save(userControllerUtil.MapBodyToConsumer(username,userToUpdate.getId()));
                userToUpdate.setConsumer(saved);
                userRepository.save(userToUpdate);
                return new ResponseEntity<Consumer>(saved, HttpStatus.CREATED);
            } catch (Exception e){
                return ExceptionResponse.ValidationError();
            }
        } else {
            return ExceptionResponse.UserNotFoundError();
        }

    }
}
