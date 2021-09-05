package co.com.gsdd.dw2.model.hateoas;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;

@Generated
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class DigimonXAttackModel extends RepresentationModel<DigimonXAttackModel> {

	@ApiModelProperty(required = true, value = "Registered digimon id")
	@PositiveOrZero(message = "digimonId should be positive")
	@NotNull(message = "digimon should not be null")
	private Long digimonId;

	@ApiModelProperty(required = true, value = "Registered attack id")
	@PositiveOrZero(message = "attackId should be positive")
	@NotNull(message = "attack should not be null")
	private Long attackId;
}
