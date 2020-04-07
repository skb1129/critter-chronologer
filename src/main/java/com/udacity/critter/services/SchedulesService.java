package com.udacity.critter.services;

import com.udacity.critter.entities.Customer;
import com.udacity.critter.entities.Employee;
import com.udacity.critter.entities.Pet;
import com.udacity.critter.entities.Schedule;
import com.udacity.critter.repositories.CustomersRepository;
import com.udacity.critter.repositories.EmployeesRepository;
import com.udacity.critter.repositories.PetsRepository;
import com.udacity.critter.repositories.SchedulesRepository;
import com.udacity.critter.schedule.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchedulesService {

    @Autowired
    private SchedulesRepository schedulesRepository;

    @Autowired
    private PetsRepository petsRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private CustomersRepository customersRepository;

    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = schedulesRepository.findAll();
        return schedules.stream().map(this::getDTOModel).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getAllSchedulesForPet(long petId) {
        Pet pet = petsRepository.getOne(petId);
        List<Schedule> schedules = schedulesRepository.getAllByPetsContains(pet);
        return schedules.stream().map(this::getDTOModel).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getAllSchedulesForEmployee(long employeeId) {
        Employee employee = employeesRepository.getOne(employeeId);
        List<Schedule> schedules = schedulesRepository.getAllByEmployeesContains(employee);
        return schedules.stream().map(this::getDTOModel).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getAllSchedulesForCustomer(long customerId) {
        Customer customer = customersRepository.getOne(customerId);
        List<Schedule> schedules = schedulesRepository.getAllByPetsIn(customer.getPets());
        return schedules.stream().map(this::getDTOModel).collect(Collectors.toList());
    }

    public ScheduleDTO saveSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        List<Employee> employees = employeesRepository.findAllById(scheduleDTO.getEmployeeIds());
        schedule.setEmployees(employees);
        List<Pet> pets = petsRepository.findAllById(scheduleDTO.getPetIds());
        schedule.setPets(pets);
        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());
        schedule = schedulesRepository.save(schedule);
        return getDTOModel(schedule);
    }

    private ScheduleDTO getDTOModel(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());
        return scheduleDTO;
    }
}
