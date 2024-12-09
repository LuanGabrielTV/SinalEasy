package com.sinalez.sinaleasy_back.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_Signal")
@Getter
@Setter
public class Signal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID signalId;

    @NotBlank private String name;
    @NotBlank private String description;
    @NotBlank private String address;
    @NotNull private Double latitude;
    @NotNull private Double longitude;
    @NotNull private Integer typeOfSignal;
    @NotNull private Integer status;
    private LocalDate date;
    @Transient private Integer numberOfLikes;
    @Transient private Integer scaleFactor;

    @JsonIgnoreProperties("signs")
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @JsonIgnoreProperties("signs")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @OneToMany(mappedBy = "signal", cascade = CascadeType.ALL, orphanRemoval = true) //pq nao funciona
    private List<Milestone> signalMilestones;

    @JsonIgnoreProperties("signs")
    @OneToOne
    @JoinColumn(name = "signalrating_id")
    private SignalRating signalRating;

    public void setCity(City cityOfSignal) {
        if (cityOfSignal == null) {
            throw new IllegalArgumentException("Cidade não pode ser nula!");
        }
        this.city = cityOfSignal;
    }

    public void setSignalMilestone(Milestone milestone) {
        if(milestone == null) {
            throw new IllegalArgumentException("Novo status não pode ser nulo!");
        }
        this.signalMilestones.add(milestone);
    }

}