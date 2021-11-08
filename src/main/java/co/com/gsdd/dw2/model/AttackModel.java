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
public class AttackModel {

	private Long attackId;
	
	@ApiModelProperty(required = true, example = "Necro Magic")
	@NotEmpty(message = "attack name should not be empty")
	private String name;
	
	@ApiModelProperty(required = true, example = "17")
	@PositiveOrZero(message = "attackTypeId should be positive")
	@NotNull(message = "attack type should not be null")
	private Long attackTypeId;
	
	@ApiModelProperty(required = true, example = "631")
	@PositiveOrZero(message = "attackTargetTypeId should be positive")
	@NotNull(message = "attack target type should not be null")
	private Long attackTargetTypeId;
	
	@ApiModelProperty(required = true, example = "12", value = "Magical Points")
	@PositiveOrZero(message = "Magical points (MP) should be positive")
	private Integer mp;

}
