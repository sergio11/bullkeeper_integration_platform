package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.NewParentEmailShouldNotExist;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ShouldBeTheCurrentMail;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.Extended;

/**
 * 
 * @author ssanchez
 *
 */
public final class ChangeUserEmailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Current Email
     */
	@NotBlank(message="{user.email.not.null}")
    @Email(message="{user.email.invalid}")
	@ShouldBeTheCurrentMail(message="{not.current.email}", groups = Extended.class)
    @JsonProperty("current_email")
    private String currentEmail;

    /**
     * New Email
     */
    @NotBlank(message="{user.email.not.null}")
    @Email(message="{user.email.invalid}")
    @NewParentEmailShouldNotExist(message="{user.email.unique}", groups = Extended.class)
    @JsonProperty("new_email")
    private String newEmail;
    
    
    public ChangeUserEmailDTO() {}

    /**
     * 
     * @param currentEmail
     * @param newEmail
     */
	public ChangeUserEmailDTO(final String currentEmail, final String newEmail) {
		super();
		this.currentEmail = currentEmail;
		this.newEmail = newEmail;
	}
	
	

	public String getCurrentEmail() {
		return currentEmail;
	}



	public void setCurrentEmail(String currentEmail) {
		this.currentEmail = currentEmail;
	}



	public String getNewEmail() {
		return newEmail;
	}



	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}



	@Override
	public String toString() {
		return "ChangeUserEmailDTO [currentEmail=" + currentEmail + ", newEmail=" + newEmail + "]";
	}

}
