
package es.bisite.usal.bulltect.persistence.constraints.group;

/**
 *
 * @author sergio
 */
public interface IGroups {

    public interface IValidEmail {}
    public interface IEmailShouldExist {}
    public interface IAccountShouldActive {}
    public interface IAccountShouldNotActive {}
    public interface IAccountShouldNotLocked {}
    public interface IShouldNotBeAFacebookUser {}
    public interface IShouldNotBeAGoogleUser {}
    
}
