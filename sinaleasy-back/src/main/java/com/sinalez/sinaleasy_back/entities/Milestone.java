package com.sinalez.sinaleasy_back.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_Milestone")
@Getter
@Setter
public class Milestone implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID milestoneId;

    @NotNull int status;
    LocalDate statusUpdateTime;

    @ManyToOne
    @JoinColumn(name = "signal_id", nullable = false)
    private Signal signal;
}