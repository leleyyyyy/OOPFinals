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
public class User implements UserDetails {

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
    private String role;

    @Getter
    @Setter
    private String emergencyContact;

    @Getter
    @Setter
    private String bloodType;

    @Getter
    @Setter
    @Column(length = 900)
    private String imageUrl;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(() -> "ROLE_" + this.role);
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(){
        this.imageUrl = "https://i.imgur.com/0000000.png";
    }
}
