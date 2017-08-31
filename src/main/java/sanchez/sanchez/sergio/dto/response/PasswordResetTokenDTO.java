package sanchez.sanchez.sergio.dto.response;

import java.io.Serializable;

public final class PasswordResetTokenDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;
	private String user;
	private String expiryDate;
	
	public PasswordResetTokenDTO(){}
	
	public PasswordResetTokenDTO(String token, String user, String expiryDate) {
		super();
		this.token = token;
		this.user = user;
		this.expiryDate = expiryDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
}
