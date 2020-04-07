package com.udacity.critter.entities;

import com.udacity.critter.pet.PetType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table
@Data
@NoArgsConstructor
public class Pet implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    private PetType type;

    private String name;

    @ManyToOne(targetEntity = Customer.class)
    private Customer customer;

    private LocalDate birthDate;

    private String notes;
}
