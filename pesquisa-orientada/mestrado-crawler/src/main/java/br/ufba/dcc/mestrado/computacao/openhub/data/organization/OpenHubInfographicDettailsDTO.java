package br.ufba.dcc.mestrado.computacao.openhub.data.organization;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import br.ufba.dcc.mestrado.computacao.openhub.data.OpenHubResultDTO;


@XStreamAlias(OpenHubOrganizationDTO.NODE_NAME)
public class OpenHubInfographicDettailsDTO implements OpenHubResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9046211186295777738L;
	
	public final static String NODE_NAME = "infographic_details";
	
	@XStreamAlias("outside_committers")
	private Long outsideCommitters;
	
	@XStreamAlias("outside_committers_commits")
	private Long outsideCommittersCommits;
	
	@XStreamAlias("projects_having_outside_commits")
	private Long projectsHavingOutsideCommits;
	
	@XStreamAlias("portfolio_projects")
	private Long portfolioProjects;
	
	@XStreamAlias("affiliators")
	private Long affiliators;
	
	@XStreamAlias("affiliators_committing_to_portfolio_projects")
	private Long affiliatorsCommittingToPortfolioProjects;
	
	@XStreamAlias("affiliator_commits_to_portfolio_projects")
	private Long affiliatorCommitsToPortfolioProjects;
	
	@XStreamAlias("affiliators_commiting_projects")
	private Long affiliatorsCommitingProjects;
	
	@XStreamAlias("outside_projects")
	private Long outsideProjects;
	
	@XStreamAlias("outside_projects_commits")
	private Long outsideProjectsCommits;
	
	@XStreamAlias("affiliators_committing_to_outside_projects")
	private Long affiliatorsCommittingToOutsideProjects;
	
	
}
