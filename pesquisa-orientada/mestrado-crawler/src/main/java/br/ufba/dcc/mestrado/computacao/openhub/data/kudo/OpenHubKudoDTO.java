package br.ufba.dcc.mestrado.computacao.openhub.data.kudo;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XmlRootElement(name = OpenHubKudoDTO.NODE_NAME)
public class OpenHubKudoDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2721389293280846206L;

	public final static String NODE_NAME = "kudo";
	

	private Date createdAt;

	private Long senderAccountId;

	private String senderAccountName;

	private Long receiverAccountId;

	private String receiverAccountName;

	private Long projectId;

	private String projectName;

	private Long contributorId;

	private String contributorName;

	@XmlElement(name = "created_at")
	@XmlSchemaType(name = "date")
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@XmlElement(name = "sender_account_id")
	public Long getSenderAccountId() {
		return senderAccountId;
	}

	public void setSenderAccountId(Long senderAccountId) {
		this.senderAccountId = senderAccountId;
	}

	@XmlElement(name = "sender_account_name")
	public String getSenderAccountName() {
		return senderAccountName;
	}

	public void setSenderAccountName(String senderAccountName) {
		this.senderAccountName = senderAccountName;
	}

	@XmlElement(name = "receiver_account_id")
	public Long getReceiverAccountId() {
		return receiverAccountId;
	}

	public void setReceiverAccountId(Long receiverAccountId) {
		this.receiverAccountId = receiverAccountId;
	}

	@XmlElement(name = "receiver_account_name")
	public String getReceiverAccountName() {
		return receiverAccountName;
	}

	public void setReceiverAccountName(String receiverAccountName) {
		this.receiverAccountName = receiverAccountName;
	}

	@XmlElement(name = "project_id")
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	@XmlElement(name = "project_name")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@XmlElement(name = "contributor_id")
	public Long getContributorId() {
		return contributorId;
	}

	public void setContributorId(Long contributorId) {
		this.contributorId = contributorId;
	}

	@XmlElement(name = "contributor_name")
	public String getContributorName() {
		return contributorName;
	}

	public void setContributorName(String contributorName) {
		this.contributorName = contributorName;
	}

}
