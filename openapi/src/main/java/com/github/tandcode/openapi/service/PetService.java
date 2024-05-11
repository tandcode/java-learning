package com.github.tandcode.openapi.service;

import com.github.tandcode.openapi.dao.PetDao;
import com.github.tandcode.openapi.model.Pet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class PetService {

  @Autowired
  private PetDao petDao;

  public boolean add(Pet pet) {
    boolean added = petDao.add(pet);
    if (added) {
      log.info("{} has been added", pet);
    } else {
      log.error("Pet with id {} is already exit", pet.getId());
    }
    return added;
  }

  public List<Pet> getAll() {
    return petDao.getAll();
  }
}
