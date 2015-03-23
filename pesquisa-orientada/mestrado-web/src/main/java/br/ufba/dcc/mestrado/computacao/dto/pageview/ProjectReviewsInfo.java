package br.ufba.dcc.mestrado.computacao.dto.pageview;

import java.io.Serializable;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubProjectEntity;


/**
 * 
 * Necessito fazer uma consulta à base de dados listando quantas reviews existem para cada projeto.
 * Essa informação terá uma agregação, e acredito que uma nova entidade não seja necessária. Entretanto,
 * preciso retornar essas informações de algum modo passível de ser utilizado numa página JSF.
 * 
 * @author leandro.ferreira
 *
 */
public class ProjectReviewsInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3315276865633299958L;

	private OpenHubProjectEntity project;
	private Long reviewsCount;

	public OpenHubProjectEntity getProject() {
		return project;
	}

	public void setProject(OpenHubProjectEntity project) {
		this.project = project;
	}

	public Long getReviewsCount() {
		return reviewsCount;
	}

	public void setReviewsCount(Long reviewsCount) {
		this.reviewsCount = reviewsCount;
	}

}
