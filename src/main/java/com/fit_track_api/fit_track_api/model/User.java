package com.fit_track_api.fit_track_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profilePic;
    private String firstName;
    private String lastName;
    private String email;
    private String password;


    private BigDecimal walletBalance = BigDecimal.ZERO;
    private Integer loyaltyPoints = 0;
}
