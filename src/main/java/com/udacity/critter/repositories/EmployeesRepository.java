package com.udacity.critter.repositories;

import com.udacity.critter.entities.Employee;
import com.udacity.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface EmployeesRepository extends JpaRepository<Employee, Long> {
    List<Employee> getAllBySkillsInAndDaysAvailableContains(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek);
}
