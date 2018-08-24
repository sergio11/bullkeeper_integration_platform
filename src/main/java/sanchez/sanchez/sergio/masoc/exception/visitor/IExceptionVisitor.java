package sanchez.sanchez.sergio.masoc.exception.visitor;

import sanchez.sanchez.sergio.masoc.exception.GetCommentsProcessException;
import sanchez.sanchez.sergio.masoc.exception.InvalidAccessTokenException;
import sanchez.sanchez.sergio.masoc.util.IVisitor;

/**
 *
 * @author sergio
 */
public interface IExceptionVisitor extends IVisitor {
    
    void visit(InvalidAccessTokenException exception);
    void visit(GetCommentsProcessException exception);
    
}
