package com.pim.develize.service;

import com.pim.develize.entity.Personnel;
import com.pim.develize.entity.Skill;
import com.pim.develize.repository.PersonnelRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonnelService {

    final
    PersonnelRepository personnelRepository;


    public PersonnelService(PersonnelRepository personnelRepository) {
        this.personnelRepository = personnelRepository;
    }

    public Personnel createPersonnel(String fname,
                                     String lname,
                                     String email,
                                     String phoneNumber,
                                     String division,
                                     String position,
                                     String status,
                                     Set<Skill> skill) {
        Personnel entity = new Personnel();
        entity.setFirstName(fname);
        entity.setLastName(lname);
        entity.setEmail(email);
        entity.setPhoneNumber(phoneNumber);
        entity.setDivision(division);
        entity.setPosition(position);
        entity.setAssignmentStatus(status);
        if (skill == null) {
            entity.setSkills(Collections.<Skill>emptySet());
        } else {
            entity.setSkills(skill);
        }
        entity.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        return personnelRepository.save(entity);
    }

    public Iterable<Personnel> getAllPersonnel(){
        return personnelRepository.findAll();
    }

    public Optional<Personnel> getPersonnelByName(String name){
        return personnelRepository.findByNameLike(name);
    }
}
