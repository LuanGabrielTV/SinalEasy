package com.sinalez.sinaleasy_back.domains;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// A relacao entre user e signal eh bidirecional. A primeira eh para a criacao de um sinal para cada usuario one to many e many to one (um usuario pode criar varios sinais, mas um sinal so pode ser criado por um usuario). Outro eh a relacao descrita em UserSignal, para relacionar a votacao many to many (um usuario pode votar em varios sinais e um sinal pode ser votado por varios usuarios) 

@Entity
@Table(name = "TB_Users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull private UUID userId;
    @NotBlank private String userLogin;
    @NotBlank private String userPassword;
    @NotBlank private String userEmail;

    @JsonBackReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Signal> userSignals;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSignal> userVotesOnSignals; // lista de votos do usuário

    public User(String userLogin, String userPassword, UserRole role) {
        this.userLogin = userLogin;
        this.userPassword = userPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna uma autoridade genérica "ROLE_USER" para todos os usuários
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }


    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return userPassword;
        // throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
    }

    @Override
    public String getUsername() {
        return userLogin;
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


}