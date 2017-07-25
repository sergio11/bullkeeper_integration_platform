package sanchez.sanchez.sergio.util;

/**
 *
 * @author sergio
 */
public interface IVisitable<T extends IVisitor> {
    void accept(T visitor);
}
