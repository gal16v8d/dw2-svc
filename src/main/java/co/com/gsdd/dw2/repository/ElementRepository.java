package co.com.gsdd.dw2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.gsdd.dw2.persistence.entities.Element;

@Repository
public interface ElementRepository extends JpaRepository<Element, Long> {

}
