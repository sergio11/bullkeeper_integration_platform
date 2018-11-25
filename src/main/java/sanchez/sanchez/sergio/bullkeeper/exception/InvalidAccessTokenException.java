package sanchez.sanchez.sergio.bullkeeper.exception;

import sanchez.sanchez.sergio.bullkeeper.exception.visitor.IExceptionVisitor;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.util.IVisitable;


/**
 *
 * @author sergio
 */
public class InvalidAccessTokenException extends IntegrationFlowException implements IVisitable<IExceptionVisitor> {
    
	private static final long serialVersionUID = 1L;
	
	private SocialMediaTypeEnum socialMediaType;
    private String accessToken;
    
   
    public InvalidAccessTokenException(String message, SocialMediaTypeEnum socialMediaType, String accessToken) {
		super(message);
		this.socialMediaType = socialMediaType;
		this.accessToken = accessToken;
	}

	public InvalidAccessTokenException(KidEntity target, SocialMediaTypeEnum socialMediaType, String accessToken) {
		super(target);
		this.socialMediaType = socialMediaType;
		this.accessToken = accessToken;
	}

	public InvalidAccessTokenException(String message, KidEntity target, SocialMediaTypeEnum socialMediaType,
			String accessToken) {
		super(message, target);
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
