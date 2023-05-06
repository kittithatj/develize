package com.pim.develize.util;

import com.pim.develize.entity.Skill;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class Utilities {
    public Set<Skill> skillToSet(Optional<Skill> skill){
        return skill.map(Collections::singleton).orElse(Collections.emptySet());
    }
}
