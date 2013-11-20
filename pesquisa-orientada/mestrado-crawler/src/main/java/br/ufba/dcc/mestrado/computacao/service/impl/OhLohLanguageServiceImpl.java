package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.language.OhLohLanguageEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.language.OhLohLanguageDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohLanguageRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohLanguageService;

@Service(OhLohLanguageServiceImpl.BEAN_NAME)
public class OhLohLanguageServiceImpl extends DefaultOhLohServiceImpl<OhLohLanguageDTO, Long, OhLohLanguageEntity>
		implements OhLohLanguageService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2789509366943106982L;
	
	public static final String BEAN_NAME =  "ohLohLanguageService";
	

	@Autowired
	public OhLohLanguageServiceImpl(@Qualifier("ohLohLanguageRepository") OhLohLanguageRepository repository) {
		super(repository, OhLohLanguageDTO.class, OhLohLanguageEntity.class);
	}

}
