package br.ufba.dcc.mestrado.computacao.web.managedbean.review;

import javax.faces.event.ActionEvent;

public interface ReviewVoting {

	void watchLikeReview(ActionEvent event);
	
	void watchDislikeReview(ActionEvent event);

}