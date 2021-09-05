package co.com.gsdd.dw2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.RepresentationModel;

import co.com.gsdd.dw2.converter.GenericConverter;

public abstract class AbstractService<T, D extends RepresentationModel<D>> {

	abstract String getSortArg();

	/**
	 * Keep old entity id for update operation.
	 * @param entityNew, build according new payload
	 * @param entityOrig, database mapped entity
	 * @return entityNew fields but with entityOrig id.
	 */
	abstract T replaceId(T entityNew, T entityOrig);

	abstract JpaRepository<T, Long> getRepo();

	abstract GenericConverter<T, D> getConverter();

	public List<D> getAll() {
		return getRepo().findAll(Sort.by(getSortArg())).stream().map(getConverter()::convertToDomain)
				.collect(Collectors.toList());
	}

	public D getById(Long id) {
		return getRepo().findById(id).map(getConverter()::convertToDomain).orElse(null);
	}

	public D save(D model) {
		return Optional.ofNullable(model).map(getConverter()::convertToEntity).map(getRepo()::saveAndFlush)
				.map(getConverter()::convertToDomain).orElse(null);
	}

	public D update(Long id, D model) {
		return getRepo().findById(id).map((T dbEntity) -> {
			T ent = getConverter().convertToEntity(model);
			return Optional.ofNullable(ent).map((T e) -> {
				e = replaceId(e, dbEntity);
				return getRepo().saveAndFlush(e);
			}).orElse(null);
		}).map(getConverter()::convertToDomain).orElse(null);
	}

	public D patch(Long id, D model) {
		return getRepo().findById(id).map(dbEntity -> getConverter().mapToEntity(model, dbEntity))
				.map((T e) -> getRepo().saveAndFlush(e)).map(getConverter()::convertToDomain).orElse(null);
	}

	public Long delete(Long id) {
		return getRepo().findById(id).map((T entity) -> {
			getRepo().delete(entity);
			return id;
		}).orElse(null);
	}

}
