package es.bisite.usal.bullytect.util;

/**
 *
 * @author sergio
 */
public interface IVisitable<T extends IVisitor> {
    void accept(T visitor);
}
