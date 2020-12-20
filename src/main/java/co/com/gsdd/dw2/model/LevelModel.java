package co.com.gsdd.dw2.model;

import javax.validation.constraints.NotEmpty;

import org.springframework.hateoas.RepresentationModel;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;

@Generated
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class LevelModel extends RepresentationModel<LevelModel> {

	private Long levelId;

	@NotEmpty(message = "level name should not be empty")
	private String name;
}
