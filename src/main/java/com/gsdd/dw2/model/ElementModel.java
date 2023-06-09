package com.gsdd.dw2.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;

@Generated
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class ElementModel {

  private Long elementId;

  @Schema(required = true, example = "Machine")
  @NotEmpty(message = "element name should not be empty")
  private String name;
}
