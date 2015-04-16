package br.ufba.dcc.mestrado.computacao.entities.recommender.criterium;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "criterium_name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecommenderCriteriumEntity other = (RecommenderCriteriumEntity) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
