package com.gsdd.dw2.repository;

import com.gsdd.dw2.persistence.entities.Attack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttackRepository extends JpaRepository<Attack, Long> {
}
