package sanchez.sanchez.sergio.bullkeeper.exception;

import sanchez.sanchez.sergio.bullkeeper.exception.visitor.IExceptionVisitor;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidEntity;
import sanchez.sanchez.sergio.bullkeeper.util.IVisitable;


/**
 *
 * @author sergio
 */
public class GetCommentsProcessException extends IntegrationFlowException implements IVisitable<IExceptionVisitor> {

  
	private static final long serialVersionUID = 1L;
	


	public GetCommentsProcessException(KidEntity target) {
		super(target);
	}

	public GetCommentsProcessException(String message, KidEntity target) {
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
