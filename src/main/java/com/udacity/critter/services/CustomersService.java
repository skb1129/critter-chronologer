package com.udacity.critter.services;

import com.udacity.critter.entities.Customer;
import com.udacity.critter.entities.Pet;
import com.udacity.critter.repositories.CustomersRepository;
import com.udacity.critter.repositories.PetsRepository;
import com.udacity.critter.user.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomersService {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private PetsRepository petsRepository;

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customersRepository.findAll();
        return customers.stream().map(this::getDTOModel).collect(Collectors.toList());
    }

    public CustomerDTO getCustomerByPetId(long petId) {
        Customer customer = petsRepository.getOne(petId).getCustomer();
        return getDTOModel(customer);
    }

    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());
        List<Pet> pets = new ArrayList<>();
        List<Long> petIds = customerDTO.getPetIds();
        if (petIds != null && !petIds.isEmpty()) {
            pets = petIds.stream().map((petId) -> petsRepository.getOne(petId)).collect(Collectors.toList());
        }
        customer.setPets(pets);
        customer = customersRepository.save(customer);
        return getDTOModel(customer);
    }

    private CustomerDTO getDTOModel(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());
        List<Long> petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }
}
