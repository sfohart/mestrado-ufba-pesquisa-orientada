package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.BaseRepository;
import br.ufba.dcc.mestrado.computacao.service.base.DefaultOhLohService;
import br.ufba.dcc.mestrado.computacao.util.ConverterHandler;

public abstract class DefaultOhLohServiceImpl<DTO extends OhLohResultDTO, ID extends Number, E extends BaseEntity<ID>>
		extends BaseOhLohServiceImpl<ID, E>
		implements DefaultOhLohService<DTO, ID, E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 382951858584438559L;
	
	private Class<DTO> dtoClass;
	private ConverterHandler<DTO, ID, E> converterUtil;
	
	
	public DefaultOhLohServiceImpl(
			BaseRepository<ID, E> baseRepository,
			Class<DTO> dtoClass, 
			Class<E> entityClass) {
		
		super(baseRepository, entityClass);
		this.dtoClass = dtoClass;
		this.converterUtil = new ConverterHandler<DTO, ID, E>(dtoClass,entityClass);
	}
	
	public Class<DTO> getDtoClass() {
		return dtoClass;
	}
	
	@Override
	@Transactional
	public E process(DTO dto) throws Exception {
		E entity = buildEntity(dto);
		
		if (entity != null) {
			validateEntity(entity);
			entity = getRepository().save(entity);
		}
		
		return entity;
	}
	
	@Transactional
	public void validateEntity(E entity) throws Exception {
		
	}
	
	public E buildEntity(DTO dto) throws Exception {
		E entity = (E) converterUtil.buildEntity(dto);
		return entity;
	}

	
}
