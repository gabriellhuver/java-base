package com.picpay.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.picpay.users.models.Transaction;
import com.picpay.users.models.User;
import com.picpay.users.repository.TransactionRepository;
import com.picpay.users.repository.UserRepository;
import com.picpay.users.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTest  extends AbstractTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    static ObjectMapper mapper;

    static FakeValuesService fakeValuesService;

    @Before
    public void contextLoads() {
        super.setUp();
        mapper = new ObjectMapper();
        fakeValuesService = new FakeValuesService(
                new Locale("pt-BR"), new RandomService());
    }

    @Test
    public void createTransaction() throws Exception{
        User user = userRepository.save( TestUtil.createDummyUser());
        User user2 = userRepository.save( TestUtil.createDummyUser());
        String uri = "/transactions";
        String inputJson = super.mapToJson(Transaction.builder().payeeId(user.getId()).payerId(user2.getId()).value(new BigDecimal(fakeValuesService.numerify("##.##"))).build());
        System.out.println(inputJson);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        userRepository.deleteById(user.getId());
        userRepository.deleteById(user2.getId());
        transactionRepository.deleteById(Long.parseLong(mvcResult.getResponse().getContentAsString().split(":")[1].split(",")[0]));
        assertEquals(201, status);
    }
    @Test
    public void createUnauthorizedTransaction() throws Exception{
        User user = userRepository.save( TestUtil.createDummyUser());
        User user2 = userRepository.save( TestUtil.createDummyUser());
        String uri = "/transactions";
        String inputJson = super.mapToJson(Transaction.builder().payeeId(user.getId()).payerId(user2.getId()).value(new BigDecimal(100)).build());
        System.out.println(inputJson);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        userRepository.deleteById(user.getId());
        userRepository.deleteById(user2.getId());
        assertEquals(401, status);
    }
    @Test
    public void createInvalidTransaction() throws Exception{
        User user = userRepository.save( TestUtil.createDummyUser());
        User user2 = userRepository.save( TestUtil.createDummyUser());
        String uri = "/transactions";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("f1wf12f2fafw")).andReturn();
        int status = mvcResult.getResponse().getStatus();
        userRepository.deleteById(user.getId());
        userRepository.deleteById(user2.getId());
        assertEquals(422, status);
    }
    @Test
    public void createNotFoundTransaction() throws Exception{
        User user = userRepository.save( TestUtil.createDummyUser());
        User user2 = userRepository.save( TestUtil.createDummyUser());
        String uri = "/transactions";
        String inputJson = super.mapToJson(Transaction.builder().payeeId(user.getId()+1).payerId(user2.getId()+1).value(new BigDecimal(fakeValuesService.numerify("##.##"))).build());
        System.out.println(inputJson);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        userRepository.deleteById(user.getId());
        userRepository.deleteById(user2.getId());
        assertEquals(404, status);
    }

    @Test
    public void findTransactionById() throws Exception{
        User user = userRepository.save( TestUtil.createDummyUser());
        User user2 = userRepository.save( TestUtil.createDummyUser());
        String uri = "/transactions";
        String inputJson = super.mapToJson(Transaction.builder().payeeId(user.getId()).payerId(user2.getId()).value(new BigDecimal(fakeValuesService.numerify("##.##"))).build());
        System.out.println(inputJson);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Long transactionId = Long.parseLong(mvcResult.getResponse().getContentAsString().split(":")[1].split(",")[0]);
        assertEquals(201, status);
        String uri2 = "/transactions/"+transactionId;
        MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.get(uri2)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status2 = mvcResult2.getResponse().getStatus();
        assertEquals(200, status2);
        userRepository.deleteById(user.getId());
        userRepository.deleteById(user2.getId());
        transactionRepository.deleteById(transactionId);
    }
    @Test
    public void findTransactionByIdNotFound() throws Exception{
        User user = userRepository.save( TestUtil.createDummyUser());
        User user2 = userRepository.save( TestUtil.createDummyUser());
        String uri = "/transactions";
        String inputJson = super.mapToJson(Transaction.builder().payeeId(user.getId()).payerId(user2.getId()).value(new BigDecimal(fakeValuesService.numerify("##.##"))).build());
        System.out.println(inputJson);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Long transactionId = Long.parseLong(mvcResult.getResponse().getContentAsString().split(":")[1].split(",")[0]);
        assertEquals(201, status);
        String uri2 = "/transactions/"+(transactionId+1);
        MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.get(uri2)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status2 = mvcResult2.getResponse().getStatus();
        assertEquals(404, status2);
        userRepository.deleteById(user.getId());
        userRepository.deleteById(user2.getId());
        transactionRepository.deleteById(transactionId);
    }
}
