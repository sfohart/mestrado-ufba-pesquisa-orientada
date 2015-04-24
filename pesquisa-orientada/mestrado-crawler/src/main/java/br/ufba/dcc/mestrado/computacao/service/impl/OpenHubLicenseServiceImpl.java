package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubLicenseEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubLicenseDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.LicenseRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.LicenseRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubLicenseService;

@Service(OpenHubLicenseServiceImpl.BEAN_NAME)
public class OpenHubLicenseServiceImpl extends DefaultOpenHubServiceImpl<OpenHubLicenseDTO, Long, OpenHubLicenseEntity>
		implements OpenHubLicenseService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2789509366943106982L;
	
	public static final String BEAN_NAME =  "ohLohLicenseService";
	

	@Autowired
	public OpenHubLicenseServiceImpl(@Qualifier(LicenseRepositoryImpl.BEAN_NAME) LicenseRepository repository) {
		super(repository, OpenHubLicenseDTO.class, OpenHubLicenseEntity.class);
	}

}
