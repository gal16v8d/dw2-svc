package co.com.gsdd.dw2.model;

import javax.validation.constraints.NotEmpty;
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
public class DigimonModel extends RepresentationModel<DigimonModel> {

	private Long digimonId;

	@NotEmpty(message = "digimon name should not be empty")
	private String name;

	@PositiveOrZero(message = "levelId should be positive")
	@NotNull(message = "digimon level (levelId) should not be null")
	private Long levelId;
	
	@PositiveOrZero(message = "digimonTypeId should be positive")
	@NotNull(message = "digimon type (digimonTypeId) should not be null")
	private Long digimonTypeId;
	
	@PositiveOrZero(message = "elementId should be positive")
	@NotNull(message = "digimon element (element) should not be null")
	private Long elementId;

}
