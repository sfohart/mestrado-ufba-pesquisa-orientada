package br.ufba.dcc.mestrado.computacao.exception;

public abstract class RecommenderException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1996079017556161389L;

	public RecommenderException() {
		super();
	}

	public RecommenderException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public RecommenderException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RecommenderException(String arg0) {
		super(arg0);
	}

	public RecommenderException(Throwable arg0) {
		super(arg0);
	}
	
	
	
}
