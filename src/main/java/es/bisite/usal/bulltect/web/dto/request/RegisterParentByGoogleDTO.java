package es.bisite.usal.bulltect.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;


public class RegisterParentByGoogleDTO extends RegisterParentDTO {
	
	@JsonProperty("google_id")
    private String googleId;
	
	@JsonProperty("picture")
    private String picture;
	
	public RegisterParentByGoogleDTO(){}

	public RegisterParentByGoogleDTO(String googleId, String picture) {
		super();
		this.googleId = googleId;
		this.picture = picture;
	}

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		return "RegisterParentByGoogleDTO [googleId=" + googleId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", birthdate=" + birthdate + ", email=" + email + ", passwordClear=" + passwordClear
				+ ", confirmPassword=" + confirmPassword + ", telephone=" + telephone + ", locale=" + locale + "]";
	}

	
	
}
