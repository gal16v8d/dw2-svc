package co.com.gsdd.dw2.model.hateoas;

import javax.validation.constraints.NotEmpty;

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
public class ElementModel extends RepresentationModel<ElementModel> {

	private Long elementId;

	@ApiModelProperty(required = true, example = "Machine")
	@NotEmpty(message = "element name should not be empty")
	private String name;
}
