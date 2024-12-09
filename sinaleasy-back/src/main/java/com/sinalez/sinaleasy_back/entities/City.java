package com.sinalez.sinaleasy_back.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_City")
@Getter
@Setter
public class City implements Serializable {
    @Id
    private String cityId;

    @NotBlank private String name;
    @NotBlank private String state;
    @NotNull private Integer rating;

    @JsonBackReference
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true) 
    private List<Signal> signs;
}


// Exemplo utilizando GIS no Postgres, caso seja necessario calcular algo no backend um dia

// @Column(columnDefinition = "POINT")
// private Point location;

// @Column(columnDefinition = "jsonb")
// private String geolocation;  // {"latitude": xx.xxxx, "longitude": yy.yyyy}

