package com.picpay.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.picpay.users.models.Consumer;
import com.picpay.users.models.Seller;
import com.picpay.users.models.User;
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

import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersTest extends AbstractTest {

    @Autowired
    UserRepository userRepository;

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
    public void CreateUser() throws Exception {
        User user = TestUtil.createDummyUser();
        String uri = "/users";

        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        int status = mvcResult.getResponse().getStatus();
        userRepository.deleteById(Long.parseLong(mvcResult.getResponse().getContentAsString().split(":")[1].split(",")[0]));
        assertEquals(201, status);
    }

    @Test
    public void CreateInvalidUser() throws Exception {
        User user = User.builder().build();
        String uri = "/users";
        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        int status = mvcResult.getResponse().getStatus();
        assertEquals(422, status);
    }
    @Test
    public void CreateDuplicatedUser() throws Exception {
        User user = TestUtil.createDummyUser();
        String uri = "/users";
        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        int status = mvcResult.getResponse().getStatus();
        int status2 = mvcResult2.getResponse().getStatus();
        userRepository.deleteById(Long.parseLong(mvcResult.getResponse().getContentAsString().split(":")[1].split(",")[0]));
        assertEquals(422, status2);
    }


    @Test
    public void CreateConsumer() throws Exception {
        User user = userRepository.save( TestUtil.createDummyUser());
        String uri = "/users/consumers";
        Consumer consumer = Consumer.builder()
                .username(fakeValuesService.bothify("######????#####?????"))
                .user(user.getId()).build();
        String inputJson = super.mapToJson(consumer);
        System.out.println(inputJson);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        System.out.println("Create consumer get status :" +status);
        userRepository.deleteById(user.getId());
        assertEquals(201, status);
    }

    @Test
    public void CreateInvalidConsumer() throws Exception {
        User user = userRepository.save( TestUtil.createDummyUser());
        String uri = "/users/consumers";
        Consumer consumer = Consumer.builder().build();
        String inputJson = super.mapToJson(consumer);
        System.out.println(inputJson);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        System.out.println("Create consumer get status :" +status);
        userRepository.deleteById(user.getId());
        assertEquals(422, status);
    }

    @Test
    public void CreateUserNotFoundConsumer() throws Exception {
        User user = userRepository.save( TestUtil.createDummyUser());
        String uri = "/users/consumers";
        Consumer consumer = Consumer.builder()
                .username(fakeValuesService.bothify("######????#####?????"))
                .user(user.getId()+1).build();
        String inputJson = super.mapToJson(consumer);
        System.out.println(inputJson);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        System.out.println("Create consumer get status :" +status);
        userRepository.deleteById(user.getId());
        assertEquals(404, status);
    }

    @Test
    public void CreateSeller() throws  Exception{
        User user = userRepository.save( TestUtil.createDummyUser());

        String uri = "/users/sellers?user_id="+user.getId();
        Seller seller = Seller.builder()
                .username(fakeValuesService.bothify("######????#####?????"))
                .cnpj(fakeValuesService.bothify("######????#####?????"))
                .fantasyName(fakeValuesService.bothify("######????#####?????"))
                .socialName(fakeValuesService.bothify("######????#####?????")).build();
        String inputJson = super.mapToJson(seller);
        System.out.println(inputJson);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        userRepository.deleteById(user.getId());
        assertEquals(201, status);
    }
    @Test
    public void CreateInvalidSeller() throws  Exception{
        User user = userRepository.save( TestUtil.createDummyUser());
        String uri = "/users/sellers?user_id="+user.getId();
        Seller seller = Seller.builder().build();
        String inputJson = super.mapToJson(seller);
        System.out.println(inputJson);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        userRepository.deleteById(user.getId());
        assertEquals(422, status);
    }
    @Test
    public void CreateSellerUserNotFound() throws  Exception{
        User user = userRepository.save( TestUtil.createDummyUser());

        String uri = "/users/sellers?user_id="+user.getId()+1;
        Seller seller = Seller.builder()
                .username(fakeValuesService.bothify("######????#####?????"))
                .cnpj(fakeValuesService.bothify("######????#####?????"))
                .fantasyName(fakeValuesService.bothify("######????#####?????"))
                .socialName(fakeValuesService.bothify("######????#####?????")).build();
        String inputJson = super.mapToJson(seller);
        System.out.println(inputJson);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        userRepository.deleteById(user.getId());
        assertEquals(404, status);
    }

    @Test
    public void FindUsers() throws Exception {
        User user = userRepository.save( TestUtil.createDummyUser());
        String uri = "/users";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        int status = mvcResult.getResponse().getStatus();
        System.out.println(mvcResult.getResponse().getContentAsString());
        userRepository.deleteById(user.getId());
        assertEquals(200, status);
    }
    @Test
    public void FindUsersByUsername() throws Exception {
        User user = userRepository.save( TestUtil.createDummyUser());
        String uri = "/users?q="+user.getFullName();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        int status = mvcResult.getResponse().getStatus();
        System.out.println(mvcResult.getResponse().getContentAsString());
        userRepository.deleteById(user.getId());
        assertEquals(200, status);
    }
    @Test
    public void FindUsersByUsernameUserNotFound() throws Exception {
        String uri = "/users?q="+fakeValuesService.bothify("##??##??##????#?#?#??#?#?????");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        int status = mvcResult.getResponse().getStatus();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertEquals(500, status);
    }
    @Test
    public void FindUserById() throws Exception {
        User user = userRepository.save( TestUtil.createDummyUser());
        String uri = "/users/"+user.getId();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        int status = mvcResult.getResponse().getStatus();
        Map<String, String> map = mapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);
        System.out.println(map.get(""));
        userRepository.deleteById(user.getId());
        assertEquals(200, status);
    }

    @Test
    public void Populate() throws Exception {
        for (int i = 0; i < 14; i++) {
            User user = userRepository.save(TestUtil.createDummyUser());
            String uri = "/users";

            String inputJson = super.mapToJson(user);
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
            System.out.println(mvcResult.getResponse().getContentAsString());
            int status = mvcResult.getResponse().getStatus();
            assertEquals(201, status);
            String uri2 = "/users/sellers?user_id="+user.getId();
            Seller seller = Seller.builder()
                    .username(fakeValuesService.bothify("######????#####?????"))
                    .cnpj(fakeValuesService.bothify("######????#####?????"))
                    .fantasyName(fakeValuesService.bothify("######????#####?????"))
                    .socialName(fakeValuesService.bothify("######????#####?????")).build();
            String inputJson2 = super.mapToJson(seller);
            System.out.println(inputJson2);
            MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.post(uri2)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson2)).andReturn();
            int status2 = mvcResult2.getResponse().getStatus();
            assertEquals(201, status2);

            String uri3 = "/users/consumers";
            Consumer consumer = Consumer.builder()
                    .username(fakeValuesService.bothify("######????#####?????"))
                    .user(user.getId()).build();
            String inputJson3 = super.mapToJson(consumer);
            System.out.println(inputJson3);
            MvcResult mvcResult3 = mvc.perform(MockMvcRequestBuilders.post(uri3)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson3)).andReturn();
            int status3 = mvcResult.getResponse().getStatus();
            System.out.println("Create consumer get status :" +status3);
            assertEquals(201, status);
        }


    }

}
