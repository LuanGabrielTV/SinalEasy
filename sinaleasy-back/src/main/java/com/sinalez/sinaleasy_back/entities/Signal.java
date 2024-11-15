package com.sinalez.sinaleasy_back.entities;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sinalez.sinaleasy_back.enums.Status;
import com.sinalez.sinaleasy_back.enums.TypeOfSignal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_Signal")
@Getter
@Setter
public class Signal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID signalId;

    private String name;
    private String description;
    private Integer typeOfSignal;
    private Integer status;

    @JsonIgnoreProperties("signs")
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
    
    // Funcao para atrelar cidade ao sinal
    public void setCity(City cityOfSignal) {
        if (cityOfSignal == null) {
            throw new IllegalArgumentException("Cidade não pode ser nula!");
        }
        this.city = cityOfSignal;
    }

}