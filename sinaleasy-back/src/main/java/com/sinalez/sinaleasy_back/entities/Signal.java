package com.sinalez.sinaleasy_back.entities;

import java.util.UUID;

import com.sinalez.sinaleasy_back.enums.Status;
import com.sinalez.sinaleasy_back.enums.TypeOfSignal;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Signal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idSignal;
    private String name;
    private String description;
    private TypeOfSignal string;
    private Status status;
    private City city;
    

}