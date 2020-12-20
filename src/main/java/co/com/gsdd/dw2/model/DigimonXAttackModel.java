package co.com.gsdd.dw2.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.hateoas.RepresentationModel;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;

@Generated
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class DigimonXAttackModel extends RepresentationModel<DigimonXAttackModel> {
	
	@PositiveOrZero(message = "digimonId should be positive")
	@NotNull(message = "digimon should not be null")
	private Long digimonId;
	
	@PositiveOrZero(message = "attackId should be positive")
	@NotNull(message = "attack should not be null")
	private Long attackId;
}
