package es.bisite.usal.bullytect.exception.visitor;

import es.bisite.usal.bullytect.exception.GetCommentsProcessException;
import es.bisite.usal.bullytect.exception.InvalidAccessTokenException;
import es.bisite.usal.bullytect.util.IVisitor;

/**
 *
 * @author sergio
 */
public interface IExceptionVisitor extends IVisitor {
    
    void visit(InvalidAccessTokenException exception);
    void visit(GetCommentsProcessException exception);
    
}
