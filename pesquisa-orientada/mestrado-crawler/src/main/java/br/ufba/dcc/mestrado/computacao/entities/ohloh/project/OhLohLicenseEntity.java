package br.ufba.dcc.mestrado.computacao.entities.ohloh.project;

import javax.persistence.Column;
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
@Table(name = OhLohLicenseEntity.NODE_NAME)
public class OhLohLicenseEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1612821573723959553L;

	public final static String NODE_NAME = "license";
	
	@Id
	@GeneratedValue
	private Long id;

	@Field(name="licenseName", index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(unique = true)
	private String name;

	@Field(name="licenseNiceName", index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(name = "nice_name")
	private String niceName;
	
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

	public String getNiceName() {
		return niceName;
	}

	public void setNiceName(String niceName) {
		this.niceName = niceName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((niceName == null) ? 0 : niceName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OhLohLicenseEntity other = (OhLohLicenseEntity) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (niceName == null) {
			if (other.niceName != null)
				return false;
		} else if (!niceName.equals(other.niceName))
			return false;
		return true;
	}

	
	
	

}
