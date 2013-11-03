package br.ufba.dcc.mestrado.computacao.entities.ohloh.project;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;

@Entity
@Table(name = OhLohLinkEntity.NODE_NAME)
public class OhLohLinkEntity implements BaseEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2167520665751768527L;

	public final static String NODE_NAME = "link";
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String category;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String title;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
