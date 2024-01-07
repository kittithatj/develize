package com.pim.develize.service;

import com.pim.develize.entity.Skill;
import com.pim.develize.exception.BaseException;
import com.pim.develize.exception.SkillException;
import com.pim.develize.model.request.SkillModel;
import com.pim.develize.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    final
    SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill createSkill(SkillModel s) throws BaseException {
        Skill skill = new Skill();
        if (s.skillName.isBlank()){
            throw SkillException.skillNameBlank();
        }else {
            skill.setSkillName(s.skillName);
            if(s.skillType == null || s.skillType.isBlank()){
                skill.setSkillType("Others");
            }else {
                skill.setSkillType(s.skillType);
            }
            return skillRepository.save(skill);
        }
    }

    public List<Skill> getAllSKills(){
        return skillRepository.findAllByOrderBySkillNameAsc();
    }

    public void deleteSkillById(Long id) throws SkillException {
        Optional<Skill> opt = skillRepository.findById(id);
        if(opt.isPresent()) {
            opt.get().removeConstrains(opt.get());
            skillRepository.delete(opt.get());
        }else{
            throw SkillException.skillNotFound();
        }
    }

}
