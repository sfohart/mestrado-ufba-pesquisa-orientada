package br.ufba.dcc.mestrado.computacao.service.impl;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.BaseRepository;
import br.ufba.dcc.mestrado.computacao.service.base.BaseOhLohService;
import br.ufba.dcc.mestrado.computacao.util.ConverterHandler;

public abstract class BaseOhLohServiceImpl<DTO extends OhLohResultDTO, ID extends Number, E extends BaseEntity<ID>>
		implements BaseOhLohService<DTO, ID, E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 382951858584438559L;
	
	private Class<DTO> dtoClass;
	private Class<E> entityClass;
	
	private BaseRepository<ID, E> repository;
	
	private ConverterHandler<DTO, ID, E> converterUtil;

	
	
	public BaseOhLohServiceImpl(
			BaseRepository<ID, E> baseRepository,
			Class<DTO> dtoClass, 
			Class<E> entityClass) {
		this.repository = baseRepository;
		this.dtoClass = dtoClass;
		this.entityClass = entityClass;
		this.converterUtil = new ConverterHandler<DTO, ID, E>(dtoClass,entityClass);
	}
	
	protected BaseRepository<ID, E> getRepository() {
		return repository;
	}
	
	@Override
	public E process(DTO dto) throws Exception {
		E entity = buildEntity(dto);
		validateEntity(entity);
		return entity;
	}
	
	public void validateEntity(E entity) throws Exception {
		
	}
	
	public E buildEntity(DTO dto) throws Exception {
		E entity = (E) converterUtil.buildEntity(dto);
		return entity;
	}

	public E saveEntity(E entity) throws Exception {
		return repository.save(entity);
	}

}
