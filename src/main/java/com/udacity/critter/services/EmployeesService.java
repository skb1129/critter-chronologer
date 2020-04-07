package com.udacity.critter.services;

import com.udacity.critter.entities.Employee;
import com.udacity.critter.repositories.EmployeesRepository;
import com.udacity.critter.user.EmployeeDTO;
import com.udacity.critter.user.EmployeeRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeesService {

    @Autowired
    private EmployeesRepository employeesRepository;

    public EmployeeDTO getEmployeeById(long employeeId) {
        Employee employee = employeesRepository.getOne(employeeId);
        return getDTOModel(employee);
    }

    public List<EmployeeDTO> getEmployeesForService(EmployeeRequestDTO requestDTO) {
        List<Employee> employees = employeesRepository.getAllBySkillsInAndDaysAvailableContains(
                requestDTO.getSkills(), requestDTO.getDate().getDayOfWeek());
        return employees.stream().map(this::getDTOModel).collect(Collectors.toList());
    }

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        employee = employeesRepository.save(employee);
        return getDTOModel(employee);
    }

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeesRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeesRepository.save(employee);
    }

    private EmployeeDTO getDTOModel(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        return employeeDTO;
    }
}
