package com.gsdd.dw2.model;

import javax.validation.constraints.NotEmpty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;

@Generated
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class AttackTypeModel {

  private Long attackTypeId;

  @ApiModelProperty(required = true, example = "Assist")
  @NotEmpty(message = "attack type name should not be empty")
  private String name;

}
