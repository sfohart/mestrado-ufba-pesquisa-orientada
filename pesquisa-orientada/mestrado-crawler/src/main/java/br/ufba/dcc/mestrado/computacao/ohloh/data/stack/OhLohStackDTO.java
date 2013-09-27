package br.ufba.dcc.mestrado.computacao.ohloh.data.stack;

import java.sql.Timestamp;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.account.OhLohAccountDTO;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableLongXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ISO8601SqlTimestampConverter;

@XStreamAlias(OhLohStackDTO.NODE_NAME)
public class OhLohStackDTO implements OhLohResultDTO {

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
	private List<OhLohStackEntryDTO> ohLohStackEntries;

	@XStreamAlias("account_id")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long acountId;
	
	@XStreamAlias("account")
	private OhLohAccountDTO account;

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

	public List<OhLohStackEntryDTO> getOhLohStackEntries() {
		return ohLohStackEntries;
	}

	public void setOhLohStackEntries(List<OhLohStackEntryDTO> ohLohStackEntries) {
		this.ohLohStackEntries = ohLohStackEntries;
	}

	public Long getAcountId() {
		return acountId;
	}

	public void setAcountId(Long acountId) {
		this.acountId = acountId;
	}

	public OhLohAccountDTO getAccount() {
		return account;
	}

	public void setAccount(OhLohAccountDTO account) {
		this.account = account;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

}
