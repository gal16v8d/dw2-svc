package com.gsdd.dw2.repository;

import com.gsdd.dw2.persistence.entities.Element;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElementRepository extends JpaRepository<Element, Long> {
}
