package com.sinalez.sinaleasy_back.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_City")
@Getter
@Setter
public class City implements Serializable {
    @Id
    private Integer cityId;

    private String name;
    private String state;
    private Integer rating;

    // O @OneToMany(mappedBy = "city") indica que a relacao eh mantida pela propriedade city na entidade Signal
    @OneToMany(mappedBy = "city") 
    private List<Signal> signs;
}


// Exemplo utilizando GIS no Postgres, caso seja necessario calcular algo no backend um dia

// @Column(columnDefinition = "POINT")
// private Point location;

// @Column(columnDefinition = "jsonb")
// private String geolocation;  // {"latitude": xx.xxxx, "longitude": yy.yyyy}