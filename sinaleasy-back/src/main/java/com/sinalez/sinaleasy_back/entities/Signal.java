package com.sinalez.sinaleasy_back.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
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
@Table(name = "TB_Signals")
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
    private LocalDate date; // Mudar esse nome para algo mais sugestivo
    @Transient private Integer numberOfLikes;
    @Transient private Integer scaleFactor;

    @JsonIgnoreProperties("signals")
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @JsonIgnoreProperties("signals")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @OneToMany(mappedBy = "signal", cascade = CascadeType.ALL, orphanRemoval = true) //pq nao funciona
    private List<Milestone> signalMilestones;

    @JsonIgnoreProperties("signals")
    @OneToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @OneToMany(mappedBy = "signal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSignal> signalVotes; // lista de votos recebidos pelo sinal


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
        if(this.signalMilestones == null){
            this.signalMilestones = new ArrayList<Milestone>();
        }
        this.signalMilestones.add(milestone);
    }

    public void updateStatus(Integer newStatus) {
        if (this.status != newStatus) {
            Milestone newMilestone = new Milestone();
            newMilestone.setStatus(newStatus);
            newMilestone.setStatusUpdateTime(LocalDate.now());
            newMilestone.setSignal(this);
            this.signalMilestones.add(newMilestone);
            this.status = newStatus;
        }
    }


    public void addGradeIfConcluded(Integer rating, String gradeDescription, LocalDate gradeUpdateTime) {
        if (this.status == 3) {
            Grade newGrade = new Grade();
            newGrade.setRating(rating);
            newGrade.setDescription(description);
            newGrade.setDate(gradeUpdateTime);
            newGrade.setSignal(this);
            this.grade = newGrade;
        }
    }

}