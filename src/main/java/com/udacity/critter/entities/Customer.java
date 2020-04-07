package com.udacity.critter.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Customer implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String phoneNumber;

    private String notes;

    @OneToMany(targetEntity = Pet.class)
    private List<Pet> pets;
}
