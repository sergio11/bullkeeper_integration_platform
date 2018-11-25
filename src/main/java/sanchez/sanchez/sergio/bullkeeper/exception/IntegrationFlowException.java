package sanchez.sanchez.sergio.bullkeeper.exception;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidEntity;

public abstract class IntegrationFlowException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	protected KidEntity target;

	public IntegrationFlowException(String message) {
		super(message);
	}

	public IntegrationFlowException(String message, KidEntity target) {
		super(message);
		this.target = target;
	}

	public IntegrationFlowException(KidEntity target) {
		super();
		this.target = target;
	}

	public KidEntity getTarget() {
		return target;
	}

	public void setTarget(KidEntity target) {
		this.target = target;
	}

}
