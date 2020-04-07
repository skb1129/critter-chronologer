package com.udacity.critter.entities;

import com.udacity.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Schedule implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @ManyToMany
    private List<Employee> employees;

    @ManyToMany
    private List<Pet> pets;

    private LocalDate date;

    @ElementCollection
    private Set<EmployeeSkill> activities;
}
