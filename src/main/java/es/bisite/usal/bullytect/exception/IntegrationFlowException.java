package es.bisite.usal.bullytect.exception;

import es.bisite.usal.bullytect.persistence.entity.SonEntity;

public abstract class IntegrationFlowException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	protected SonEntity target;

	public IntegrationFlowException(String message) {
		super(message);
	}

	public IntegrationFlowException(String message, SonEntity target) {
		super(message);
		this.target = target;
	}

	public IntegrationFlowException(SonEntity target) {
		super();
		this.target = target;
	}

	public SonEntity getTarget() {
		return target;
	}

	public void setTarget(SonEntity target) {
		this.target = target;
	}

}
