package br.ufba.dcc.mestrado.computacao.repository.base;

import java.io.Serializable;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.OhLohBaseEntity;

public interface BaseRepository<ID extends Number, E extends OhLohBaseEntity<ID>> extends Serializable {

	public Long countAll();
	public List<E> findAll();	
	public List<E> findAll(String orderBy);
	public List<E> findAll(Integer startAt, Integer offset);
	public List<E> findAll(Integer startAt, Integer offset, String orderBy);
	
	public E findById(ID id);
	public E save(E entity) throws Exception;
	public E add(E entity) throws Exception;
	public E update(E entity) throws Exception;
	public E delete(E entity)  throws Exception;
	
}
