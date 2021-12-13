package com.gsdd.dw2.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
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
public class Element {

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  @Column(name = "elementId")
  private Long elementId;

  @NotBlank(message = "element name should not be empty")
  @Column(name = "name", nullable = false, unique = true)
  private String name;

}
