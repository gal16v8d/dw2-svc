package com.gsdd.dw2.converter;

public interface GenericConverter<T, D> {

    D convertToDomain(T entity);

    T convertToEntity(D model);

    T mapToEntity(D model, T oldEntity);
}
