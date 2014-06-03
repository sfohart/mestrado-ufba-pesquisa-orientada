package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohLicenseEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.LicenseRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.LicenseRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.LicenseService;

@Service(LicenseServiceImpl.BEAN_NAME)
public class LicenseServiceImpl extends BaseServiceImpl<Long, OhLohLicenseEntity>
		implements LicenseService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2789509366943106982L;
	
	public static final String BEAN_NAME =  "licenseService";
	

	@Autowired
	public LicenseServiceImpl(@Qualifier(LicenseRepositoryImpl.BEAN_NAME) LicenseRepository repository) {
		super(repository,  OhLohLicenseEntity.class);
	}

}
