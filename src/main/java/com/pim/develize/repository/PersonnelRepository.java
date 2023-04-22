package com.pim.develize.repository;

import com.pim.develize.entity.Personnel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonnelRepository extends CrudRepository<Personnel, Long> {

    public Optional<Personnel> findByFirstName(String firstName);

    @Query(value="SELECT p FROM Personnel p where p.firstName like %:name% or p.lastName like %:name%")
    public Optional<Personnel> findByNameLike(String name);
}
