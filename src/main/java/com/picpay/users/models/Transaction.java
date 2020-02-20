package com.picpay.users.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@SuperBuilder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonProperty(value = "payee_id")
    private Long payeeId;

    @Column(nullable = false)
    @JsonProperty(value = "payer_id")
    private Long payerId;

    @Column(nullable = false)
    @JsonProperty(value = "transaction_date")
    private Date transactionDate;

    @Column(nullable = false)
    private BigDecimal value;


}
