package com.picpay.users.repository;

import com.picpay.users.models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    @Query("SELECT seller FROM Seller seller WHERE seller.username = :username")
    Optional<Seller> findByUsername(@Param(value = "username") String username);
}
