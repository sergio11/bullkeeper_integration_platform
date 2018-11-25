package sanchez.sanchez.sergio.bullkeeper.exception.visitor;

import sanchez.sanchez.sergio.bullkeeper.exception.GetCommentsProcessException;
import sanchez.sanchez.sergio.bullkeeper.exception.InvalidAccessTokenException;
import sanchez.sanchez.sergio.bullkeeper.util.IVisitor;

/**
 *
 * @author sergio
 */
public interface IExceptionVisitor extends IVisitor {
    
    void visit(InvalidAccessTokenException exception);
    void visit(GetCommentsProcessException exception);
    
}
