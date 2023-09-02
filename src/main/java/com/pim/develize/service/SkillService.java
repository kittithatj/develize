package com.pim.develize.service;

import com.pim.develize.entity.Personnel;
import com.pim.develize.entity.Skill;
import com.pim.develize.exception.BaseException;
import com.pim.develize.exception.SkillException;
import com.pim.develize.model.request.SkillModel;
import com.pim.develize.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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

    public Set<Skill> getAllSKills(){
        return skillRepository.findAll();
    }

    public void deleteSkillById(Long id) throws SkillException {
        Optional<Skill> opt = skillRepository.findById(id);

        if(!opt.isEmpty()) {
            for (Personnel p : opt.get().getPersonnels()) {
                opt.get().removePersonel(p);
            }
            skillRepository.delete(opt.get());
        }else{
            throw SkillException.deleteFailed();
        }

    }


}
