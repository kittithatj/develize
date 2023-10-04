package com.pim.develize.repository;

import com.pim.develize.entity.Personnel;
import com.pim.develize.entity.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

public interface SkillRepository extends CrudRepository<Skill,Long> {

    public Set<Skill> findAll();

    public List<Skill> findAllByOrderBySkillNameAsc();

    @Query(value = "SELECT personnel.skills FROM Personnel personnel WHERE personnel.personnel_id  = :personnel_id")
    public List<Skill> findAllByPersonnelsPersonnel_id(Long personnel_id);

    public Optional<Skill> findBySkillName(String name);

    public Optional<Skill> findBySkillType(String TypeName);

}
