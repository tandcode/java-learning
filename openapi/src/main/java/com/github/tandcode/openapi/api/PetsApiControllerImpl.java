package com.github.tandcode.openapi.api;

import com.github.tandcode.openapi.model.Pet;
import com.github.tandcode.openapi.service.PetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@Tag(name = "pets", description = "the pets API")
public class PetsApiControllerImpl implements PetsApi {

  @Autowired
  PetService petService;

  @Override
  public ResponseEntity<Pet> createPet(Pet pet) {
    boolean added = petService.add(pet);
    if (added) {
      return ResponseEntity.ok(pet);
    } else {
      return ResponseEntity.status(CONFLICT).body(pet);
    }
  }

  @Override
  public ResponseEntity<List<Pet>> listPets(Integer limit) {
    List<Pet> pets = petService.getAll();

    if(pets.size() > limit) {
      pets = pets.subList(0, limit);
    }

    return ResponseEntity.ok(pets);
  }
}
