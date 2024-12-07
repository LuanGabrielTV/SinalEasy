package com.sinalez.sinaleasy_back.entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_USERS")
@Getter
@Setter
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull private UUID userId;
    @NotBlank private String userLogin;
    @NotBlank private String userPassword;
    @NotBlank private String userEmail;

    @NotBlank private List<Signal> userSigns;

}