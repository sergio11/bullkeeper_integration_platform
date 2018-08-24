package sanchez.sanchez.sergio.masoc.exception;

import sanchez.sanchez.sergio.masoc.exception.visitor.IExceptionVisitor;
import sanchez.sanchez.sergio.masoc.persistence.entity.SonEntity;
import sanchez.sanchez.sergio.masoc.util.IVisitable;


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
