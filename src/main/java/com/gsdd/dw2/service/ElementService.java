package com.gsdd.dw2.service;

import com.gsdd.dw2.converter.GenericConverter;
import com.gsdd.dw2.model.ElementModel;
import com.gsdd.dw2.persistence.entities.Element;
import com.gsdd.dw2.repository.ElementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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
