package br.ufba.dcc.mestrado.computacao.openhub.restful.responses;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class OpenHubBaseResponse  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4034993819512511173L;
	
	public final static String SUCCESS = "success";
	public final static String FAILED = "failed";
	
	public final static String ERROR_API_KEY_EXCEDED = "This api_key has exceeded its daily access limit.";
	
	public final static String NODE_NAME = "response";
	
	
	
	private String status;
	private String error;
	
	
	private Integer itemsReturned;
	private Integer itemsAvailable;
	private Integer firstItemPosition;

	public OpenHubBaseResponse() {
		super();
	}

	@XmlElement(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@XmlElement(name = "error")
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@XmlElement(name = "items_returned")
	public Integer getItemsReturned() {
		return itemsReturned;
	}

	public void setItemsReturned(Integer itemsReturned) {
		this.itemsReturned = itemsReturned;
	}

	@XmlElement(name = "items_available")
	public Integer getItemsAvailable() {
		return itemsAvailable;
	}

	public void setItemsAvailable(Integer itemsAvailable) {
		this.itemsAvailable = itemsAvailable;
	}

	@XmlElement(name = "first_item_position")
	public Integer getFirstItemPosition() {
		return firstItemPosition;
	}

	public void setFirstItemPosition(Integer firstItemPosition) {
		this.firstItemPosition = firstItemPosition;
	}
	
	public boolean isStatusSuccess() {
		if (SUCCESS.equals(getStatus())) {
			return true;
		}
		
		return false;
	}

}