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
import jakarta.validation.constraints.NotBlank;
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
public class Evolution {

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  @Column(name = "evolutionId")
  private Long evolutionId;

  @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "baseDigimonId", referencedColumnName = "digimonId",
      foreignKey = @ForeignKey(name = "Fk_digimon_base"))
  private Digimon baseDigimon;

  @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "evolvedDigimonId", referencedColumnName = "digimonId",
      foreignKey = @ForeignKey(name = "Fk_digimon_evolved"))
  private Digimon evolvedDigimon;

  @NotBlank(message = "evolution should have dna factor")
  @Column(name = "dnaTimes", nullable = false)
  private String dnaTimes;
}
