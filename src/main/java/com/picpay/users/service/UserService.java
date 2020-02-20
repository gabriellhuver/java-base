package com.picpay.users.service;

import com.picpay.users.exception.ExceptionResponse;
import com.picpay.users.models.User;
import com.picpay.users.repository.UserRepository;
import com.picpay.users.util.UserControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserControllerUtil userControllerUtil;

    public ResponseEntity<?> createUser(User userBody){
        try {
            // No swagger diz que o password volta no response porém
            // eu acho que não pode voltar e tem que virar um hash
            // createdUser.setPassword(null);
            User createdUser = userRepository.save(userBody);
            return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            if (e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                return ExceptionResponse.ValidationError();
            }
            return ExceptionResponse.InternalError();
        }

    }
    public ResponseEntity<?> FindUser(String query){
        if (query == null) {
            List<User> users = new ArrayList<>();
            userRepository.findAll(Sort.by("fullName").ascending()).forEach(users::add);
            if(users.isEmpty()){
                return ExceptionResponse.InternalError();
            }
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        } else {
            List<User> users =  userRepository.findByFullName(query);
            users.addAll(userRepository.findByUserName(query));
            if(users.isEmpty()){
                return ExceptionResponse.InternalError();
            }
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        }
    }
    public ResponseEntity<?> findById(Long user_id){
          if(user_id != null){
            Optional<User> byId = userRepository.findById(user_id);
            if(byId.isPresent()){
                User user = byId.get();
                return new ResponseEntity<>(userControllerUtil.UserToDetailedUser(user), HttpStatus.OK);
            }
            return ExceptionResponse.UserNotFoundError();
        }
        return ExceptionResponse.InternalError();
    }
}
