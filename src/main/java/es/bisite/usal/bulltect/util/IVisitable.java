package es.bisite.usal.bulltect.util;

/**
 *
 * @author sergio
 */
public interface IVisitable<T extends IVisitor> {
    void accept(T visitor);
}
