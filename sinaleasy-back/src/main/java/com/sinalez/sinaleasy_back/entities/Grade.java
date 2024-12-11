package com.sinalez.sinaleasy_back.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_Grade")
@Getter
@Setter

public class Grade implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID gradeId;

    @NotNull private Integer rating;
    @NotBlank private String description;
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "signal_id", nullable = false)
    private Signal signal;
}
