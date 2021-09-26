package co.com.gsdd.dw2.model;

import javax.validation.constraints.NotEmpty;
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
public class DigimonModel {

	private Long digimonId;

	@ApiModelProperty(required = true, example = "MetalGreymon")
	@NotEmpty(message = "digimon name should not be empty")
	private String name;

	@ApiModelProperty(required = true, value = "Registered level id")
	@PositiveOrZero(message = "levelId should be positive")
	@NotNull(message = "digimon level (levelId) should not be null")
	private Long levelId;

	@ApiModelProperty(required = true, value = "Registered digimon type id")
	@PositiveOrZero(message = "digimonTypeId should be positive")
	@NotNull(message = "digimon type (digimonTypeId) should not be null")
	private Long digimonTypeId;

	@ApiModelProperty(required = true, value = "Registered element id")
	@PositiveOrZero(message = "elementId should be positive")
	@NotNull(message = "digimon element (element) should not be null")
	private Long elementId;

}
