package co.com.gsdd.dw2.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.converter.GenericConverter;
import co.com.gsdd.dw2.model.hateoas.ElementModel;
import co.com.gsdd.dw2.persistence.entities.Element;
import co.com.gsdd.dw2.repository.ElementRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/elements")
public class ElementController extends AbstractController<Element, ElementModel> {

	private final ElementRepository elementRepository;
	private final GenericConverter<Element, ElementModel> elementConverter;

	@Override
	public String getSortArg() {
		return "elementId";
	}
	
	@Override
	public Long getId(Element entity) {
		return entity.getElementId();
	}
	
	@Override
	public Element replaceId(Element entityNew, Element entityOrig) {
		entityNew.setElementId(entityOrig.getElementId());
		return entityNew;
	}

	@Override
	public JpaRepository<Element, Long> getRepo() {
		return elementRepository;
	}

	@Override
	public GenericConverter<Element, ElementModel> getConverter() {
		return elementConverter;
	}

}
