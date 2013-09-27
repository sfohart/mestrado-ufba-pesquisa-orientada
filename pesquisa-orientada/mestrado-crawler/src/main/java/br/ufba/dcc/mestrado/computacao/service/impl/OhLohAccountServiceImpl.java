package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.account.OhLohAccountEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.account.OhLohAccountDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAccountRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohAccountService;

@Service
public class OhLohAccountServiceImpl extends BaseOhLohServiceImpl<OhLohAccountDTO, Long, OhLohAccountEntity>
		implements OhLohAccountService {

	@Autowired
	public OhLohAccountServiceImpl(OhLohAccountRepository repository) {
		super(repository, OhLohAccountDTO.class, OhLohAccountEntity.class);
	}
	
	public Long countAll() {
		return getRepository().countAll();
	}
	
	public OhLohAccountEntity findById(Long id) {
		return getRepository().findById(id);
	}
	
	public List<OhLohAccountEntity> findAll(Integer startAt, Integer offset) {
		return getRepository().findAll(startAt, offset);
	}
	
	
	@Override
	public OhLohAccountEntity process(OhLohAccountDTO dto) throws Exception{
		OhLohAccountEntity entity = super.process(dto);
		getRepository().save(entity);
		return entity;
	}

}
