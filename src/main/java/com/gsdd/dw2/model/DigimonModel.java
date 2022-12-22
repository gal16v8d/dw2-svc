package com.gsdd.dw2.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;

@Generated
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class DigimonModel {

  private Long digimonId;

  @Schema(required = true, example = "MetalGreymon")
  @NotEmpty(message = "digimon name should not be empty")
  private String name;

  @Schema(required = true, description = "Registered level id")
  @PositiveOrZero(message = "levelId should be positive")
  @NotNull(message = "digimon level (levelId) should not be null")
  private Long levelId;

  @Schema(required = true, description = "Registered digimon type id")
  @PositiveOrZero(message = "digimonTypeId should be positive")
  @NotNull(message = "digimon type (digimonTypeId) should not be null")
  private Long digimonTypeId;

  @Schema(required = true, description = "Registered element id")
  @PositiveOrZero(message = "elementId should be positive")
  @NotNull(message = "digimon element (element) should not be null")
  private Long elementId;
}
