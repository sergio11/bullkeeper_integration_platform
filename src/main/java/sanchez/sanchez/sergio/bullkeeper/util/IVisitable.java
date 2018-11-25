package sanchez.sanchez.sergio.bullkeeper.util;

/**
 *
 * @author sergio
 */
public interface IVisitable<T extends IVisitor> {
    void accept(T visitor);
}
