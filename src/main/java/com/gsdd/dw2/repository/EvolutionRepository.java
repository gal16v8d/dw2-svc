package com.gsdd.dw2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gsdd.dw2.persistence.entities.Evolution;

@Repository
public interface EvolutionRepository extends JpaRepository<Evolution, Long> {

}
