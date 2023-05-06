package com.pim.develize.repository;

import com.pim.develize.entity.Skill;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface SkillRepository extends CrudRepository<Skill,Long> {

    public Set<Skill> findAll();
    public Optional<Skill> findBySkillName(String name);

    public Optional<Skill> findBySkillType(String TypeName);

}
