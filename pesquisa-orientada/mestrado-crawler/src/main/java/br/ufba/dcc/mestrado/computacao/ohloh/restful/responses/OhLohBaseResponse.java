package br.ufba.dcc.mestrado.computacao.ohloh.restful.responses;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class OhLohBaseResponse {
	public final static String SUCCESS = "success";
	public final static String FAILED = "failed";
	
	public final static String ERROR_API_KEY_EXCEDED = "This api_key has exceeded its daily access limit.";
	
	public final static String NODE_NAME = "response";
	
	private String status;
	private String error;
	@XStreamAlias("items_returned")
	private Integer itemsReturned;
	@XStreamAlias("items_available")
	private Integer itemsAvailable;
	@XStreamAlias("first_item_position")
	private Integer firstItemPosition;

	public OhLohBaseResponse() {
		super();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getItemsReturned() {
		return itemsReturned;
	}

	public void setItemsReturned(Integer itemsReturned) {
		this.itemsReturned = itemsReturned;
	}

	public Integer getItemsAvailable() {
		return itemsAvailable;
	}

	public void setItemsAvailable(Integer itemsAvailable) {
		this.itemsAvailable = itemsAvailable;
	}

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