package co.com.gsdd.dw2.model;

import javax.validation.constraints.NotBlank;
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
public class EvolutionModel extends RepresentationModel<EvolutionModel> {
	
	private Long evolutionId;

	@PositiveOrZero(message = "baseDigimonId should be positive")
	@NotNull(message = "baseDigimonId should not be null")
	private Long baseDigimonId;
	
	@PositiveOrZero(message = "evolvedDigimonId should be positive")
	@NotNull(message = "evolvedDigimonId should not be null")
	private Long evolvedDigimonId;
	
	@NotBlank(message = "evolution should have dna factor")
	private String dnaTimes;
}
