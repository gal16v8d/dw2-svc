package com.gsdd.dw2.repository;

import com.gsdd.dw2.persistence.entities.Digimon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DigimonRepository extends JpaRepository<Digimon, Long> {}
