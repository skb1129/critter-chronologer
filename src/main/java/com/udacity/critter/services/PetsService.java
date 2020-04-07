package com.udacity.critter.services;

import com.udacity.critter.entities.Pet;
import com.udacity.critter.pet.PetDTO;
import com.udacity.critter.repositories.CustomersRepository;
import com.udacity.critter.repositories.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetsService {

    @Autowired
    private PetsRepository petsRepository;

    @Autowired
    private CustomersRepository customersRepository;

    public List<PetDTO> getAllPets() {
        List<Pet> pets = petsRepository.findAll();
        return pets.stream().map(this::getDTOModel).collect(Collectors.toList());
    }

    public List<PetDTO> getPetsByCustomerId(long customerId) {
        List<Pet> pets = petsRepository.getAllByCustomerId(customerId);
        return pets.stream().map(this::getDTOModel).collect(Collectors.toList());
    }

    public PetDTO getPetById(long petId) {
        Pet pet = petsRepository.getOne(petId);
        return getDTOModel(pet);
    }

    public PetDTO savePet(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setCustomer(customersRepository.getOne(petDTO.getOwnerId()));
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        pet = petsRepository.save(pet);
        return getDTOModel(pet);
    }

    private PetDTO getDTOModel(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setType(pet.getType());
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());
        return petDTO;
    }
}
