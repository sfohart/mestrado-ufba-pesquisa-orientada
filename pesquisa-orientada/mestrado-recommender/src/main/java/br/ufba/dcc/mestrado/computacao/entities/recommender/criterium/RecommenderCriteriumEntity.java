package br.ufba.dcc.mestrado.computacao.entities.recommender.criterium;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;

@Entity
@Table(name = RecommenderCriteriumEntity.NODE_NAME)
public class RecommenderCriteriumEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5844752060128051697L;

	public final static String NODE_NAME = "recommender_criterium";
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
