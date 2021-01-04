package co.com.gsdd.dw2.model.hateoas;

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
public class AttackModel extends RepresentationModel<AttackModel> {

	private Long attackId;
	
	@NotEmpty(message = "attack name should not be empty")
	private String name;
	
	@PositiveOrZero(message = "attackTypeId should be positive")
	@NotNull(message = "attack type should not be null")
	private Long attackTypeId;
	
	@PositiveOrZero(message = "Magical points (MP) should be positive")
	private Integer mp;

}
