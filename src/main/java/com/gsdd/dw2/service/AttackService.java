package com.gsdd.dw2.service;

import com.gsdd.dw2.converter.GenericConverter;
import com.gsdd.dw2.model.AttackModel;
import com.gsdd.dw2.persistence.entities.Attack;
import com.gsdd.dw2.repository.AttackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AttackService extends AbstractService<Attack, AttackModel> {

    private final AttackRepository attackRepository;
    private final GenericConverter<Attack, AttackModel> attackConverter;

    @Override
    public String getSortArg() {
        return "attackId";
    }

    @Override
    public Attack replaceId(Attack entityNew, Attack entityOrig) {
        entityNew.setAttackId(entityOrig.getAttackId());
        return entityNew;
    }

    @Override
    public JpaRepository<Attack, Long> getRepo() {
        return attackRepository;
    }

    @Override
    public GenericConverter<Attack, AttackModel> getConverter() {
        return attackConverter;
    }
}
