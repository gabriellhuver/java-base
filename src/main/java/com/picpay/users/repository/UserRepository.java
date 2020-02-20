package com.picpay.users.repository;


import com.picpay.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT obj FROM User obj WHERE obj.fullName LIKE :name%")
    List<User> findByFullName(@Param(value = "name") String name);

    @Query("SELECT obj FROM User obj WHERE obj.consumer.username LIKE :name% OR obj.seller.username LIKE :name% ")
    List<User> findByUserName(@Param(value = "name") String name);

}
