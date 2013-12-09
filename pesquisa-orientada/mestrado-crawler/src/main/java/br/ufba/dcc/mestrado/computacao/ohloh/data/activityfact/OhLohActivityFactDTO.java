package br.ufba.dcc.mestrado.computacao.ohloh.data.activityfact;

import java.sql.Timestamp;

import br.ufba.dcc.mestrado.computacao.ohloh.data.OhLohResultDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohProjectDTO;
import br.ufba.dcc.mestrado.computacao.xstream.converters.NullableLongXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ISO8601SqlTimestampConverter;

@XStreamAlias(OhLohActivityFactDTO.NODE_NAME)
public class OhLohActivityFactDTO implements OhLohResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1220433997459663718L;

	public final static String NODE_NAME = "activity_fact";

	@XStreamConverter(value = ISO8601SqlTimestampConverter.class)
	private Timestamp month;

	@XStreamAlias("code_added")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long codeAdded;

	@XStreamAlias("code_removed")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long codeRemoved;

	@XStreamAlias("comments_added")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long commentsAdded;

	@XStreamAlias("comments_removed")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long commentsRemoved;

	@XStreamAlias("blanks_added")
	private Long blanksAdded;

	@XStreamAlias("blanks_removed")
	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long blanksRemoved;

	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long commits;

	@XStreamConverter(value=NullableLongXStreamConverter.class)
	private Long contributors;
	
	private Long projectId;
	
	private OhLohProjectDTO ohlohProject;

	public Timestamp getMonth() {
		return month;
	}

	public void setMonth(Timestamp month) {
		this.month = month;
	}

	public Long getCodeAdded() {
		return codeAdded;
	}

	public void setCodeAdded(Long codeAdded) {
		this.codeAdded = codeAdded;
	}

	public Long getCodeRemoved() {
		return codeRemoved;
	}

	public void setCodeRemoved(Long codeRemoved) {
		this.codeRemoved = codeRemoved;
	}

	public Long getCommentsAdded() {
		return commentsAdded;
	}

	public void setCommentsAdded(Long commentsAdded) {
		this.commentsAdded = commentsAdded;
	}

	public Long getCommentsRemoved() {
		return commentsRemoved;
	}

	public void setCommentsRemoved(Long commentsRemoved) {
		this.commentsRemoved = commentsRemoved;
	}

	public Long getBlanksAdded() {
		return blanksAdded;
	}

	public void setBlanksAdded(Long blanksAdded) {
		this.blanksAdded = blanksAdded;
	}

	public Long getBlanksRemoved() {
		return blanksRemoved;
	}

	public void setBlanksRemoved(Long blanksRemoved) {
		this.blanksRemoved = blanksRemoved;
	}

	public Long getCommits() {
		return commits;
	}

	public void setCommits(Long commits) {
		this.commits = commits;
	}

	public Long getContributors() {
		return contributors;
	}

	public void setContributors(Long contributors) {
		this.contributors = contributors;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public OhLohProjectDTO getOhlohProject() {
		return ohlohProject;
	}

	public void setOhlohProject(OhLohProjectDTO ohlohProject) {
		this.ohlohProject = ohlohProject;
	}

	
	
}
