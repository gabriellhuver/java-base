package com.picpay.users.util;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.picpay.users.models.User;

import java.util.Locale;

public class TestUtil {

    public static User createDummyUser() {
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("pt-BR"), new RandomService());
        return User.builder()
                .cpf(fakeValuesService.bothify("###.###.###-##"))
                .email(fakeValuesService.bothify("?#?#?.#?#?#@gmail.com"))
                .fullName(fakeValuesService.bothify("????? ????????"))
                .phone_number("+5527995203552")
                .password(fakeValuesService.bothify("##??##??##?##??##"))
                .build();
    }


}
