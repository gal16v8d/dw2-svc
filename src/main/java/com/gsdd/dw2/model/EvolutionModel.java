package com.gsdd.dw2.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;

@Generated
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class EvolutionModel {

  private Long evolutionId;

  @ApiModelProperty(required = true, value = "Digimon id for base form")
  @PositiveOrZero(message = "baseDigimonId should be positive")
  @NotNull(message = "baseDigimonId should not be null")
  private Long baseDigimonId;

  @ApiModelProperty(required = true, value = "Digimon id for evolved form")
  @PositiveOrZero(message = "evolvedDigimonId should be positive")
  @NotNull(message = "evolvedDigimonId should not be null")
  private Long evolvedDigimonId;

  @ApiModelProperty(required = true, value = "DNA amount for properly evolve")
  @NotBlank(message = "evolution should have dna factor")
  private String dnaTimes;
}
