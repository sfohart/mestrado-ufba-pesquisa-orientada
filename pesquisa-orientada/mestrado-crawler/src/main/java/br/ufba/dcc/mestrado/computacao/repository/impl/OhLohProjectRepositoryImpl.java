package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohProjectRepository;

@Repository
public class OhLohProjectRepositoryImpl extends BaseRepositoryImpl<Long, OhLohProjectEntity>
		implements OhLohProjectRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public OhLohProjectRepositoryImpl() {
		super(OhLohProjectEntity.class);
	}

	@Override
	public OhLohProjectEntity findById(Long id) {		
		OhLohProjectEntity result = super.findById(id);
		
		if (result != null) {
			result.getOhLohLicenses();
			result.getOhLohTags();
		}
		
		return result;
	}
	
}
