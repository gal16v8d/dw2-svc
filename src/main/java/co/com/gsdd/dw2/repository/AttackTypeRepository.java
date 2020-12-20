package co.com.gsdd.dw2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.gsdd.dw2.persistence.entities.AttackType;

@Repository
public interface AttackTypeRepository extends JpaRepository<AttackType, Long> {

}
