package com.java.ne_starter.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java.ne_starter.models.Base;
import com.java.ne_starter.models.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigInteger;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}), @UniqueConstraint(columnNames = {"username"})})
@NoArgsConstructor
@AllArgsConstructor
public class User extends Base {

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name="national_id")
    private String nationalId;

    @Column(name = "username")
    private String username;

    @Transient
    private String fullName;

    public String getFullName() {
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
    }

    @JsonIgnore
    @Column(name = "password", nullable = true)
    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(String username, String email, String password,String nationalId,String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nationalId = nationalId;
        this.phoneNumber = phoneNumber;
    }
}
