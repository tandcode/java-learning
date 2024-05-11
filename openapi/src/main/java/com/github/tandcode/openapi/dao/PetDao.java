package com.github.tandcode.openapi.dao;

import com.github.tandcode.openapi.model.Pet;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PetDao {

  private final Map<Long, Pet> datastore = new LinkedHashMap<>();

  public PetDao() {
    add(new Pet(0L, "cat"));
    add(new Pet(1L, "dog"));
  }

  public boolean add(Pet pet) {
    boolean notYetPresent = !datastore.containsKey(pet.getId());
    if (notYetPresent) {
      datastore.put(pet.getId(), pet);
    }
    return notYetPresent;
  }

  public Pet get(long index) {
    return datastore.get(index);
  }

  public List<Pet> getAll() {
    return new ArrayList<>(datastore.values()); // Return a copy to avoid external modifications
  }

}
