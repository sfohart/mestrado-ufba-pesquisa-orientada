package br.ufba.dcc.mestrado.computacao.recommender;

import java.io.Serializable;

import org.apache.mahout.cf.taste.model.Preference;

public class CriteriumPreference implements Preference, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6614504925449511786L;
	
	private long userID;
	private long itemID;
	private long criteriumID;
	private float value;

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public long getItemID() {
		return itemID;
	}

	public void setItemID(long itemID) {
		this.itemID = itemID;
	}

	public long getCriteriumID() {
		return criteriumID;
	}

	public void setCriteriumID(long criteriumID) {
		this.criteriumID = criteriumID;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (criteriumID ^ (criteriumID >>> 32));
		result = prime * result + (int) (itemID ^ (itemID >>> 32));
		result = prime * result + (int) (userID ^ (userID >>> 32));
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
		CriteriumPreference other = (CriteriumPreference) obj;
		if (criteriumID != other.criteriumID)
			return false;
		if (itemID != other.itemID)
			return false;
		if (userID != other.userID)
			return false;
		return true;
	}
	
	

}
