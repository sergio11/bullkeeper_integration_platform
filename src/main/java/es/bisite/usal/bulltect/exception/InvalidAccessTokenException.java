package es.bisite.usal.bulltect.exception;

import es.bisite.usal.bulltect.exception.visitor.IExceptionVisitor;
import es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;
import es.bisite.usal.bulltect.util.IVisitable;


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

	public InvalidAccessTokenException(SonEntity target, SocialMediaTypeEnum socialMediaType, String accessToken) {
		super(target);
		this.socialMediaType = socialMediaType;
		this.accessToken = accessToken;
	}

	public InvalidAccessTokenException(String message, SonEntity target, SocialMediaTypeEnum socialMediaType,
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
