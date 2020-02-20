package com.picpay.users.repository;

import com.picpay.users.models.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
    @Query("SELECT consumer FROM Consumer consumer WHERE consumer.username = :username")
    Optional<Consumer> findByUsername(@Param(value = "username") String username);
}
