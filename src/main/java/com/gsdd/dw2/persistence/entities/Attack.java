package com.gsdd.dw2.persistence.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
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
public class Attack {

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  @Column(name = "attackId")
  private Long attackId;

  @NotEmpty(message = "attack name should not be empty")
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "attackTypeId", referencedColumnName = "attackTypeId",
      foreignKey = @ForeignKey(name = "Fk_attack_type"))
  private AttackType attackType;

  @ManyToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "attackTargetTypeId", referencedColumnName = "attackTargetTypeId",
      foreignKey = @ForeignKey(name = "Fk_attack_target_type"))
  private AttackTargetType attackTargetType;

  @PositiveOrZero(message = "Magical points (MP) should be positive")
  @Column(name = "mp", nullable = false)
  private Integer mp;
}
