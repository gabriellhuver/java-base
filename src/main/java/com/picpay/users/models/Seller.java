package com.picpay.users.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@SuperBuilder
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cnpj;

    @Column(nullable = false)
    @JsonProperty(value = "fantasy_name")
    private String fantasyName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    @JsonProperty(value = "user_id")
    private Long user;

    @Column(nullable = false)
    @JsonProperty(value = "social_name")
    private String socialName;



}
