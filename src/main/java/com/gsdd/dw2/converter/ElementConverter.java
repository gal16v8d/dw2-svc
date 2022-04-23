package com.gsdd.dw2.converter;

import com.gsdd.dw2.model.ElementModel;
import com.gsdd.dw2.persistence.entities.Element;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ElementConverter implements GenericConverter<Element, ElementModel> {

    @Override
    public ElementModel convertToDomain(Element entity) {
        return Optional.ofNullable(entity)
                .map(
                        e ->
                                ElementModel.builder()
                                        .elementId(e.getElementId())
                                        .name(e.getName())
                                        .build())
                .orElse(null);
    }

    @Override
    public Element convertToEntity(ElementModel model) {
        return Optional.ofNullable(model)
                .map(m -> Element.builder().elementId(m.getElementId()).name(m.getName()).build())
                .orElse(null);
    }

    @Override
    public Element mapToEntity(ElementModel model, Element oldEntity) {
        Element newEntity = Element.builder().elementId(oldEntity.getElementId()).build();
        newEntity.setName(
                Optional.ofNullable(model)
                        .map(ElementModel::getName)
                        .orElseGet(oldEntity::getName));
        return newEntity;
    }
}
