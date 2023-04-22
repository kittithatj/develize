package com.pim.develize.repository;

import com.pim.develize.entity.Skill;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SkillRepository extends CrudRepository<Skill,Long> {

    public Optional<Skill> findBySkillName(String name);

    public Optional<Skill> findBySkillType(String TypeName);

}
