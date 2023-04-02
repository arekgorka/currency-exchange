package com.kantor.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "USERS")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "FIRSTNAME")
    private String firstname;

    @NotNull
    @Column(name = "LASTNAME")
    private String lastname;

    @NotNull
    @Column(name = "LOGIN", unique = true)
    private String login;

    @NotNull
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Email
    @Column(name = "MAIL", unique = true)
    private String mail;

    @Column(name = "CREATED")
    private LocalDate created;

    @OneToMany(
            targetEntity = AccountBalance.class,
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private List<AccountBalance> accountBalances;

    @OneToMany(
            targetEntity = Transaction.class,
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private List<Transaction> transactions;

    @OneToMany(
            targetEntity = Order.class,
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private List<Order> orders;

    public User(String firstname, String lastname, String login, String password, String mail) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
        this.mail = mail;
        this.created = LocalDate.now();
        this.accountBalances = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public User(Long id, String firstname, String lastname, String login, String password, String mail) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
        this.mail = mail;
    }
}
