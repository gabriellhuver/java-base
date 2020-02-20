package com.picpay.users.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@SuperBuilder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    @JsonProperty("full_name")
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone_number;

    @OneToOne(targetEntity = Consumer.class,fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Consumer consumer;

    @OneToOne(targetEntity = Seller.class,fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Seller seller;

}
