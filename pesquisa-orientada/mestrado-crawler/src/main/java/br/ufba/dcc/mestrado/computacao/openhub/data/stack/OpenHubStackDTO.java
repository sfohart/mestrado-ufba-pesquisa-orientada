package br.ufba.dcc.mestrado.computacao.openhub.data.stack;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.account.OpenHubAccountDTO;

@XmlRootElement(name = OpenHubStackDTO.NODE_NAME)
public class OpenHubStackDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1367617631361506867L;

	public final static String NODE_NAME = "stack";
	
	private String id;

	private String title;

	private String description;

	private Date updatedAt;

	private Long projectCount;

	private List<OpenHubStackEntryDTO> stackEntries;

	private Long acountId;
	
	private OpenHubAccountDTO account;

	@XmlElement(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name = "updated_at")
	@XmlSchemaType(name = "date")
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@XmlElement(name = "project_count")
	public Long getProjectCount() {
		return projectCount;
	}
	
	public void setProjectCount(Long projectCount) {
		this.projectCount = projectCount;
	}

	@XmlElement(name = "stack_entries")
	public List<OpenHubStackEntryDTO> getStackEntries() {
		return stackEntries;
	}
	
	public void setStackEntries(List<OpenHubStackEntryDTO> stackEntries) {
		this.stackEntries = stackEntries;
	}

	@XmlElement(name = "account_id")
	public Long getAcountId() {
		return acountId;
	}

	public void setAcountId(Long acountId) {
		this.acountId = acountId;
	}

	@XmlElement(name = "account")
	public OpenHubAccountDTO getAccount() {
		return account;
	}

	public void setAccount(OpenHubAccountDTO account) {
		this.account = account;
	}

	@XmlID
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

}
