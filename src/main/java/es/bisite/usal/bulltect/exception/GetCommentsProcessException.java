package es.bisite.usal.bulltect.exception;

import es.bisite.usal.bulltect.exception.visitor.IExceptionVisitor;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;
import es.bisite.usal.bulltect.util.IVisitable;


/**
 *
 * @author sergio
 */
public class GetCommentsProcessException extends IntegrationFlowException implements IVisitable<IExceptionVisitor> {

  
	private static final long serialVersionUID = 1L;
	


	public GetCommentsProcessException(SonEntity target) {
		super(target);
	}

	public GetCommentsProcessException(String message, SonEntity target) {
		super(message, target);
	}

	public GetCommentsProcessException(String message) {
		super(message);
	}


	@Override
    public void accept(IExceptionVisitor visitor) {
        visitor.visit(this);
    }
}
