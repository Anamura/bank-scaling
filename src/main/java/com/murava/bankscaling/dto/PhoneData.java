package com.murava.bankscaling.dto;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "phone_data")
public class PhoneData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String phone;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}