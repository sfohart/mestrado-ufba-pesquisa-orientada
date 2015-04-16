package br.ufba.dcc.mestrado.computacao.openhub.data.stack;

import java.sql.Timestamp;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.account.OpenHubAccountDTO;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableLongXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ISO8601SqlTimestampConverter;

@XStreamAlias(OpenHubStackDTO.NODE_NAME)
public class OpenHubStackDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1367617631361506867L;

	public final static String NODE_NAME = "stack";
	
	@XStreamAsAttribute	
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long id;

	private String title;

	private String description;

	@XStreamConverter(value = ISO8601SqlTimestampConverter.class)
	@XStreamAlias("updated_at")
	private Timestamp updatedAt;

	@XStreamAlias("project_count")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long projectCount;

	@XStreamAlias("stack_entries")
	private List<OpenHubStackEntryDTO> stackEntries;

	@XStreamAlias("account_id")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long acountId;
	
	@XStreamAlias("account")
	private OpenHubAccountDTO account;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getProjectCount() {
		return projectCount;
	}

	public void setProjectCount(Long projectCount) {
		this.projectCount = projectCount;
	}

	public List<OpenHubStackEntryDTO> getStackEntries() {
		return stackEntries;
	}
	
	public void setStackEntries(List<OpenHubStackEntryDTO> stackEntries) {
		this.stackEntries = stackEntries;
	}

	public Long getAcountId() {
		return acountId;
	}

	public void setAcountId(Long acountId) {
		this.acountId = acountId;
	}

	public OpenHubAccountDTO getAccount() {
		return account;
	}

	public void setAccount(OpenHubAccountDTO account) {
		this.account = account;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

}
