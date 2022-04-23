package com.gsdd.dw2.repository;

import com.gsdd.dw2.persistence.entities.Evolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvolutionRepository extends JpaRepository<Evolution, Long> {}
