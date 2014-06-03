package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohLicenseEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohLicenseDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.LicenseRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.LicenseRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohLicenseService;

@Service(OhLohLicenseServiceImpl.BEAN_NAME)
public class OhLohLicenseServiceImpl extends DefaultOhLohServiceImpl<OhLohLicenseDTO, Long, OhLohLicenseEntity>
		implements OhLohLicenseService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2789509366943106982L;
	
	public static final String BEAN_NAME =  "ohLohLicenseService";
	

	@Autowired
	public OhLohLicenseServiceImpl(@Qualifier(LicenseRepositoryImpl.BEAN_NAME) LicenseRepository repository) {
		super(repository, OhLohLicenseDTO.class, OhLohLicenseEntity.class);
	}

}
