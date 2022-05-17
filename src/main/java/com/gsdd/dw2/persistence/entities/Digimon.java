package com.gsdd.dw2.persistence.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
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
public class Digimon {

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  @Column(name = "digimonId")
  private Long digimonId;

  @NotEmpty(message = "digimon name should not be empty")
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(
      name = "levelId",
      referencedColumnName = "levelId",
      foreignKey = @ForeignKey(name = "Fk_level"))
  private Level level;

  @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(
      name = "digimonTypeId",
      referencedColumnName = "digimonTypeId",
      foreignKey = @ForeignKey(name = "Fk_type"))
  private DigimonType digimonType;

  @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(
      name = "elementId",
      referencedColumnName = "elementId",
      foreignKey = @ForeignKey(name = "Fk_element"))
  private Element element;
}
