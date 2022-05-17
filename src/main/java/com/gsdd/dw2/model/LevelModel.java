package com.gsdd.dw2.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;

@Generated
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class LevelModel {

  private Long levelId;

  @Schema(required = true, example = "Rookie")
  @NotEmpty(message = "level name should not be empty")
  private String name;
}
