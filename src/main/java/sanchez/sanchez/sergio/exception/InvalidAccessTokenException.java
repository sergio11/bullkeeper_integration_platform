package sanchez.sanchez.sergio.exception;

import sanchez.sanchez.sergio.exception.visitor.IExceptionVisitor;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.util.IVisitable;


/**
 *
 * @author sergio
 */
public class InvalidAccessTokenException extends RuntimeException implements IVisitable<IExceptionVisitor> {
    
    private SocialMediaTypeEnum socialMediaType;
    private String accessToken;

    public InvalidAccessTokenException(SocialMediaTypeEnum socialMediaType, String accessToken) {
        this.socialMediaType = socialMediaType;
        this.accessToken = accessToken;
    }

    public SocialMediaTypeEnum getSocialMediaType() {
        return socialMediaType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public void accept(IExceptionVisitor visitor) {
        visitor.visit(this);
    }
}
