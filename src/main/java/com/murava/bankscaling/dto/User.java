package com.murava.bankscaling.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Date date_of_birth;

    @ToString.Exclude
    @Size(min = 0, max = 500)
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<EmailData> emails;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<PhoneData> phones;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<EmailData> getEmail() {
        return this.emails;
    }

    public void setEmail(List<EmailData> emails) {
        this.emails = emails;
    }

    public List<PhoneData> getPhone() {
        return this.phones;
    }

    public void setPhone(List<PhoneData> phones) {
        this.phones = phones;
    }
}