package com.pim.develize.api;

import com.pim.develize.entity.Personnel;
import com.pim.develize.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personnel")
public class PersonnelApi {

    @Autowired
    PersonnelService personnelService;

    @GetMapping("/get")
    public ResponseEntity<Iterable<Personnel>> getAllPersonnel(){
        Iterable<Personnel> response = personnelService.getAllPersonnel();
        return ResponseEntity.ok(response);
    }
}
