package com.udacity.critter.entities;

import com.udacity.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Table
public class Employee implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ElementCollection
    private Set<EmployeeSkill> skills;

    @ElementCollection
    private Set<DayOfWeek> daysAvailable;
}
