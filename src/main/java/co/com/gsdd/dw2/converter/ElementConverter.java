package co.com.gsdd.dw2.converter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import co.com.gsdd.dw2.model.ElementModel;
import co.com.gsdd.dw2.persistence.entities.Element;

@Component
public class ElementConverter implements GenericConverter<Element, ElementModel> {

	@Override
	public ElementModel convertToDomain(Element entity) {
		return Optional.ofNullable(entity)
				.map(e -> ElementModel.builder().elementId(e.getElementId()).name(e.getName()).build())
				.orElse(null);
	}

	@Override
	public Element convertToEntity(ElementModel model) {
		return Optional.ofNullable(model)
				.map(m -> Element.builder().elementId(m.getElementId()).name(m.getName()).build())
				.orElse(null);
	}

}
