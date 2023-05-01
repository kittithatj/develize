package com.pim.develize;

import com.pim.develize.entity.Personnel;
import com.pim.develize.entity.Skill;
import com.pim.develize.exception.BaseException;
import com.pim.develize.repository.PersonnelRepository;
import com.pim.develize.repository.SkillRepository;
import com.pim.develize.service.PersonnelService;
import com.pim.develize.service.SkillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;


@SpringBootTest
public class TestPersonnelService {

    @Autowired
    PersonnelService personnelService;
    @Autowired
    SkillService skillService;
    @Autowired
    SkillRepository skillRepository;
    @Autowired
    PersonnelRepository personnelRepository;

    @Test
    public void testCreatePersonnel() throws BaseException {
        skillService.createSkill("Java",null);
        Optional<Skill> java = skillRepository.findBySkillName("Java");
        System.out.println(java.get().getSkillName());
        Set<Skill> javaToSet = java.map(Collections::singleton).orElse(Collections.emptySet());
        personnelService.createPersonnel(TestData.firstName,TestData.lastName,TestData.email,TestData.phoneNumber,TestData.division,TestData.position,TestData.status,javaToSet);
    }

    interface TestData {
        String firstName = "Atid";
        String lastName = "Kingkoiklang";
        String email = "jarunyabuyam007@gmail.com";
        String phoneNumber = "0112223333";
        String division = "Cleaning";
        String position = "Manager";

        String status = "Fired";
    }



}
