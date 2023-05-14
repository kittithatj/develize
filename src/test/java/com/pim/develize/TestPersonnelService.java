package com.pim.develize;

import com.pim.develize.exception.BaseException;
import com.pim.develize.repository.PersonnelRepository;
import com.pim.develize.repository.SkillRepository;
import com.pim.develize.service.PersonnelService;
import com.pim.develize.service.SkillService;
import com.pim.develize.util.Utilities;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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

    @Autowired
    Utilities util;

    @Test
    public void testCreatePersonnel() throws BaseException {

    }

}
