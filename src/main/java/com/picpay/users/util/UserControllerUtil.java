package com.picpay.users.util;

import com.picpay.users.models.Consumer;
import com.picpay.users.models.Seller;
import com.picpay.users.models.User;
import com.picpay.users.models.dto.DetailedUserDTO;
import com.picpay.users.repository.ConsumerRepository;
import com.picpay.users.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UserControllerUtil {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    ConsumerRepository consumerRepository;
    public DetailedUserDTO UserToDetailedUser(User user){
        HashMap<String,Object> accounts = new HashMap<>();
        if(user.getConsumer() != null) accounts.put("consumer",user.getConsumer());
        if(user.getSeller() != null) accounts.put("seller",user.getSeller());
        user.setSeller(null); user.setConsumer(null);
        DetailedUserDTO detailedUserDTO =
                DetailedUserDTO.builder()
                        .accounts(accounts)
                        .user(user).build();
        return detailedUserDTO;
    }

    public boolean IsValidUsername(String username){
        Optional<Consumer> consumer =  consumerRepository.findByUsername(username);
        if(consumer.isPresent()){return false;}
        Optional<Seller> seller =  sellerRepository.findByUsername(username);
        if(seller.isPresent()){return false;}
        return true;
    }
    public Seller MapBodyToSeller(Map<String, Object> body, Long query){
       return Seller.builder()
                .username((String) body.get("username"))
                .socialName((String) body.get("social_name"))
                .fantasyName((String) body.get("fantasy_name"))
                .cnpj((String) body.get("cnpj"))
                .user(query).build();
    }
    public Consumer MapBodyToConsumer(String username, Long userId){
        return Consumer.builder()
                .username(username)
                .user(userId).build();
    }
}
