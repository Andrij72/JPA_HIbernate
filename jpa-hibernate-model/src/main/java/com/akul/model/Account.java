package com.akul.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "balance")
    private BigDecimal balance = BigDecimal.ZERO;
}
