package com.gsdd.dw2.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Level {

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  @Column(name = "levelId")
  private Long levelId;

  @NotBlank(message = "level name should not be empty")
  @Column(name = "name", nullable = false, unique = true)
  private String name;
}
