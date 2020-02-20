package com.picpay.users.util;

import com.picpay.users.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class TransactionControllerUtil {

    @Autowired
    RestTemplate restTemplate;

    @Value("${authorization.url}")
    private String BASE_URL;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public int AuthorizeTransaction(Transaction transaction){
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<Transaction> entity = new HttpEntity<Transaction>(transaction,headers);
            return restTemplate.exchange(
                    BASE_URL + "/transactions/authorization", HttpMethod.POST, entity, String.class).getStatusCode().value();
        } catch (HttpClientErrorException ex){
            return ex.getRawStatusCode();
        }
    }


}
