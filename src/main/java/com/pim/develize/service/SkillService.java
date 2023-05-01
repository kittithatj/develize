package com.pim.develize.service;

import com.pim.develize.entity.Skill;
import com.pim.develize.entity.User;
import com.pim.develize.exception.BaseException;
import com.pim.develize.exception.SkillException;
import com.pim.develize.exception.UserException;
import com.pim.develize.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillService {

    final
    SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill createSkill(String skillName, String skillType) throws BaseException {
        Skill skill = new Skill();
        if (skillName.isBlank()){
            throw SkillException.skillNameBalnk();
        }else {
            skill.setSkillName(skillName);
            if(skillType == null){
                skill.setSkillType("ETC.");
            }else {
                skill.setSkillType(skillType);
            }
            return skillRepository.save(skill);
        }
    }


}
