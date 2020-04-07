package com.udacity.critter.entities;

import com.udacity.critter.pet.PetType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table
public class Pet implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    private PetType type;

    private String name;

    @ManyToOne
    private Customer customer;

    private LocalDate birthDate;

    private String notes;
}
