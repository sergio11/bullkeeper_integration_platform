package sanchez.sanchez.sergio.exception;

import sanchez.sanchez.sergio.exception.visitor.IExceptionVisitor;
import sanchez.sanchez.sergio.util.IVisitable;


/**
 *
 * @author sergio
 */
public class GetCommentsProcessException extends RuntimeException implements IVisitable<IExceptionVisitor> {

    public GetCommentsProcessException(Throwable cause) {
        super(cause);
    }

    @Override
    public void accept(IExceptionVisitor visitor) {
        visitor.visit(this);
    }
}
