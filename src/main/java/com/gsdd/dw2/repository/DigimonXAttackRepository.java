package com.gsdd.dw2.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gsdd.dw2.persistence.entities.Attack;
import com.gsdd.dw2.persistence.entities.Digimon;
import com.gsdd.dw2.persistence.entities.DigimonXAttack;
import com.gsdd.dw2.persistence.entities.DigimonXAttackPK;

@Repository
public interface DigimonXAttackRepository extends JpaRepository<DigimonXAttack, DigimonXAttackPK> {

  @Query("SELECT dxa FROM DigimonXAttack dxa WHERE dxa.id.digimon = ?1")
  List<DigimonXAttack> findByDigimon(Digimon digimon);

  @Query("SELECT dxa FROM DigimonXAttack dxa WHERE dxa.id.digimon = ?1 AND dxa.id.attack = ?2")
  DigimonXAttack findByDigimonAndAttack(Digimon digimon, Attack attack);

}
