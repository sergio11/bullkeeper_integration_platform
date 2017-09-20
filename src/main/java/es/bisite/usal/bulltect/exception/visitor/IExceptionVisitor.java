package es.bisite.usal.bulltect.exception.visitor;

import es.bisite.usal.bulltect.exception.GetCommentsProcessException;
import es.bisite.usal.bulltect.exception.InvalidAccessTokenException;
import es.bisite.usal.bulltect.util.IVisitor;

/**
 *
 * @author sergio
 */
public interface IExceptionVisitor extends IVisitor {
    
    void visit(InvalidAccessTokenException exception);
    void visit(GetCommentsProcessException exception);
    
}
