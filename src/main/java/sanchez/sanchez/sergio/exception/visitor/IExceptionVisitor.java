package sanchez.sanchez.sergio.exception.visitor;

import sanchez.sanchez.sergio.exception.GetCommentsProcessException;
import sanchez.sanchez.sergio.exception.InvalidAccessTokenException;
import sanchez.sanchez.sergio.util.IVisitor;

/**
 *
 * @author sergio
 */
public interface IExceptionVisitor extends IVisitor {
    
    void visit(InvalidAccessTokenException exception);
    void visit(GetCommentsProcessException exception);
    
}
