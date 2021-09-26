package co.com.gsdd.dw2.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import co.com.gsdd.dw2.converter.GenericConverter;
import co.com.gsdd.dw2.model.ElementModel;
import co.com.gsdd.dw2.persistence.entities.Element;
import co.com.gsdd.dw2.repository.ElementRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ElementService extends AbstractService<Element, ElementModel> {
	
	private final ElementRepository elementRepository;
	private final GenericConverter<Element, ElementModel> elementConverter;

	@Override
	public String getSortArg() {
		return "elementId";
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
