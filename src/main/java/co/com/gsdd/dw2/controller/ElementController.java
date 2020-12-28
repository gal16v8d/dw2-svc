package co.com.gsdd.dw2.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.converter.GenericConverter;
import co.com.gsdd.dw2.model.ElementModel;
import co.com.gsdd.dw2.persistence.entities.Element;
import co.com.gsdd.dw2.repository.ElementRepository;

@RefreshScope
@RestController
@RequestMapping("v1/elements")
public class ElementController extends AbstractController<Element, ElementModel> {

	private final ElementRepository elementRepository;
	private final GenericConverter<Element, ElementModel> elementConverter;

	@Autowired
	public ElementController(GenericConverter<Element, ElementModel> elementConverter, ElementRepository elementRepository) {
		this.elementConverter = elementConverter;
		this.elementRepository = elementRepository;
	}

	@Override
	public String getSortArg() {
		return "elementId";
	}
	
	@Override
	public Long getId(Element entity) {
		return entity.getElementId();
	}

	@Override
	public JpaRepository<Element, Long> getRepo() {
		return elementRepository;
	}

	@Override
	public GenericConverter<Element, ElementModel> getConverter() {
		return elementConverter;
	}

	@PutMapping("{elementId:[0-9]+}")
	public ResponseEntity<ElementModel> update(@PathVariable("elementId") Long elementId,
			@Valid @RequestBody ElementModel model) {
		return getRepo().findById(elementId).map((Element dbEntity) -> {
			Element el = getConverter().convertToEntity(model);
			return Optional.ofNullable(el).map((Element e) -> {
				e.setElementId(dbEntity.getElementId());
				return getRepo().saveAndFlush(e);
			}).orElse(null);
		}).map(this::defineModelWithLinks).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
