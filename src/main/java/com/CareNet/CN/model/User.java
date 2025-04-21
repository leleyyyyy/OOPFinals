package com.CareNet.CN.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Table(name = "records")

public class User {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Getter
    @Setter
    @Column(nullable = false, length = 25)
    private String middleName;

    @Getter
    @Setter
    @Column(nullable = false, length = 10)
    private String gender;

    @Getter
    @Setter
    @Column(nullable = false, length = 20)
    private String birthday;

    @Getter
    @Setter
    @Column(nullable = false, length = 11)
    private String phoneNumber;

    @Getter
    @Setter
    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false, length = 64)
    private String password;

    @Getter
    @Setter
    @Column(nullable = false, length = 25)
    private String firstName;

    @Getter
    @Setter
    @Column(nullable = false, length = 25)
    private String lastName;

    @Getter
    @Setter
    @Column(nullable = false)
    private String role; // Use the Role enum
}



