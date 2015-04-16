package br.ufba.dcc.mestrado.computacao.openhub.data.kudo;

import java.sql.Timestamp;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableLongXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ISO8601SqlTimestampConverter;

@XStreamAlias(OpenHubKudoDTO.NODE_NAME)
public class OpenHubKudoDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2721389293280846206L;

	public final static String NODE_NAME = "kudo";
	
	@XStreamAsAttribute	
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long id;

	@XStreamConverter(value = ISO8601SqlTimestampConverter.class)
	@XStreamAlias("created_at")
	private Timestamp createdAt;

	@XStreamAlias("sender_account_id")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long senderAccountId;

	@XStreamAlias("sender_account_name")
	private String senderAccountName;

	@XStreamAlias("receiver_account_id")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long receiverAccountId;

	@XStreamAlias("receiver_account_name")
	private String receiverAccountName;

	@XStreamAlias("project_id")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long projectId;

	@XStreamAlias("project_name")
	private String projectName;

	@XStreamAlias("contributor_id")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long contributorId;

	@XStreamAlias("contributor_name")
	private String contributorName;

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Long getSenderAccountId() {
		return senderAccountId;
	}

	public void setSenderAccountId(Long senderAccountId) {
		this.senderAccountId = senderAccountId;
	}

	public String getSenderAccountName() {
		return senderAccountName;
	}

	public void setSenderAccountName(String senderAccountName) {
		this.senderAccountName = senderAccountName;
	}

	public Long getReceiverAccountId() {
		return receiverAccountId;
	}

	public void setReceiverAccountId(Long receiverAccountId) {
		this.receiverAccountId = receiverAccountId;
	}

	public String getReceiverAccountName() {
		return receiverAccountName;
	}

	public void setReceiverAccountName(String receiverAccountName) {
		this.receiverAccountName = receiverAccountName;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getContributorId() {
		return contributorId;
	}

	public void setContributorId(Long contributorId) {
		this.contributorId = contributorId;
	}

	public String getContributorName() {
		return contributorName;
	}

	public void setContributorName(String contributorName) {
		this.contributorName = contributorName;
	}

}
