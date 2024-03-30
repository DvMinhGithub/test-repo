package com.example.demo.entity;

import java.sql.Timestamp;

import com.example.demo.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Name is not blank")
    private String name;

    @Column(name = "email",unique = true)
    @NotBlank(message = "Email is not blank")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "dob")
    private Timestamp dob;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "has_delete")
    private Boolean hasDelete = false;
}
