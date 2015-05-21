package br.ufba.dcc.mestrado.computacao.openhub.data.organization;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;

@XmlRootElement(name = OpenHubOrganizationDTO.NODE_NAME)
public class OpenHubInfographicDettailsDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9046211186295777738L;

	public final static String NODE_NAME = "infographic_details";

	private Long outsideCommitters;
	private Long outsideCommittersCommits;
	private Long projectsHavingOutsideCommits;
	private Long portfolioProjects;
	private Long affiliators;
	private Long affiliatorsCommittingToPortfolioProjects;
	private Long affiliatorCommitsToPortfolioProjects;
	private Long affiliatorsCommitingProjects;
	private Long outsideProjects;
	private Long outsideProjectsCommits;
	private Long affiliatorsCommittingToOutsideProjects;

	@XmlElement(name = "outside_committers")
	public Long getOutsideCommitters() {
		return outsideCommitters;
	}

	public void setOutsideCommitters(Long outsideCommitters) {
		this.outsideCommitters = outsideCommitters;
	}

	@XmlElement(name = "outside_committers_commits")
	public Long getOutsideCommittersCommits() {
		return outsideCommittersCommits;
	}

	
	public void setOutsideCommittersCommits(Long outsideCommittersCommits) {
		this.outsideCommittersCommits = outsideCommittersCommits;
	}

	@XmlElement(name = "projects_having_outside_commits")
	public Long getProjectsHavingOutsideCommits() {
		return projectsHavingOutsideCommits;
	}

	public void setProjectsHavingOutsideCommits(
			Long projectsHavingOutsideCommits) {
		this.projectsHavingOutsideCommits = projectsHavingOutsideCommits;
	}

	@XmlElement(name = "portfolio_projects")
	public Long getPortfolioProjects() {
		return portfolioProjects;
	}

	public void setPortfolioProjects(Long portfolioProjects) {
		this.portfolioProjects = portfolioProjects;
	}

	@XmlElement(name = "affiliators")
	public Long getAffiliators() {
		return affiliators;
	}

	public void setAffiliators(Long affiliators) {
		this.affiliators = affiliators;
	}

	@XmlElement(name = "affiliators_committing_to_portfolio_projects")
	public Long getAffiliatorsCommittingToPortfolioProjects() {
		return affiliatorsCommittingToPortfolioProjects;
	}

	public void setAffiliatorsCommittingToPortfolioProjects(
			Long affiliatorsCommittingToPortfolioProjects) {
		this.affiliatorsCommittingToPortfolioProjects = affiliatorsCommittingToPortfolioProjects;
	}

	@XmlElement(name = "affiliator_commits_to_portfolio_projects")
	public Long getAffiliatorCommitsToPortfolioProjects() {
		return affiliatorCommitsToPortfolioProjects;
	}

	public void setAffiliatorCommitsToPortfolioProjects(
			Long affiliatorCommitsToPortfolioProjects) {
		this.affiliatorCommitsToPortfolioProjects = affiliatorCommitsToPortfolioProjects;
	}

	@XmlElement(name = "affiliators_commiting_projects")
	public Long getAffiliatorsCommitingProjects() {
		return affiliatorsCommitingProjects;
	}

	public void setAffiliatorsCommitingProjects(
			Long affiliatorsCommitingProjects) {
		this.affiliatorsCommitingProjects = affiliatorsCommitingProjects;
	}

	@XmlElement(name = "outside_projects")
	public Long getOutsideProjects() {
		return outsideProjects;
	}

	public void setOutsideProjects(Long outsideProjects) {
		this.outsideProjects = outsideProjects;
	}

	@XmlElement(name = "outside_projects_commits")
	public Long getOutsideProjectsCommits() {
		return outsideProjectsCommits;
	}

	public void setOutsideProjectsCommits(Long outsideProjectsCommits) {
		this.outsideProjectsCommits = outsideProjectsCommits;
	}

	@XmlElement(name = "affiliators_committing_to_outside_projects")
	public Long getAffiliatorsCommittingToOutsideProjects() {
		return affiliatorsCommittingToOutsideProjects;
	}

	public void setAffiliatorsCommittingToOutsideProjects(
			Long affiliatorsCommittingToOutsideProjects) {
		this.affiliatorsCommittingToOutsideProjects = affiliatorsCommittingToOutsideProjects;
	}

}
