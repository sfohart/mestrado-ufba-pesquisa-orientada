package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.language.OhLohLanguageEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.language.OhLohLanguageDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohLanguageRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohLanguageService;

@Service(OhLohLanguageServiceImpl.BEAN_NAME)
public class OhLohLanguageServiceImpl extends BaseOhLohServiceImpl<OhLohLanguageDTO, Long, OhLohLanguageEntity>
		implements OhLohLanguageService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2789509366943106982L;
	
	public static final String BEAN_NAME =  "ohLohLanguageService";
	
	private Logger logger = Logger.getLogger(OhLohLanguageServiceImpl.class.getName());

	@Autowired
	public OhLohLanguageServiceImpl(OhLohLanguageRepository repository) {
		super(repository, OhLohLanguageDTO.class, OhLohLanguageEntity.class);
	}

	
	public Long countAll() {
		return getRepository().countAll();
	}
	
	public OhLohLanguageEntity findById(Long id) {
		return getRepository().findById(id);
	}
	
	public List<OhLohLanguageEntity> findAll(Integer startAt, Integer offset) {
		return getRepository().findAll(startAt, offset);
	}
	
	@Override
	public OhLohLanguageEntity process(OhLohLanguageDTO dto) throws Exception{
		OhLohLanguageEntity entity = super.process(dto);
		logger.info(String.format("Persistindo linguagem %s (%d)", entity.getName(), entity.getId()));
		getRepository().save(entity);
		return entity;
	}

}
