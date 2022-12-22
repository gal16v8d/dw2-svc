package com.gsdd.dw2.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class DigimonType {

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  @Column(name = "digimonTypeId")
  private Long digimonTypeId;

  @NotEmpty(message = "digimon type name should not be empty")
  @Column(name = "name", nullable = false, unique = true)
  private String name;
}
