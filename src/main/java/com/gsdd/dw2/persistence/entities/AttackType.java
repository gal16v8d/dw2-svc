package com.gsdd.dw2.persistence.entities;

import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Generated
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AttackType {

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  @Column(name = "attackTypeId")
  private Long attackTypeId;

  @NotEmpty(message = "attack type name should not be empty")
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @OneToMany(mappedBy = "attackType", fetch = FetchType.LAZY)
  private Set<Attack> attacks;
}
