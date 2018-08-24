package sanchez.sanchez.sergio.masoc.util;

/**
 *
 * @author sergio
 */
public interface IVisitable<T extends IVisitor> {
    void accept(T visitor);
}
