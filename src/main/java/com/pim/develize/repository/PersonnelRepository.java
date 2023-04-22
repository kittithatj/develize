package com.pim.develize.repository;

import com.pim.develize.entity.Personnel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonnelRepository extends CrudRepository<Personnel, Long> {

    Optional<Personnel> findByFirstName(String firstName);
}
