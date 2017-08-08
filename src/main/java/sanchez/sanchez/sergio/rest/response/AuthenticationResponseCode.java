package sanchez.sanchez.sergio.rest.response;

public enum AuthenticationResponseCode implements IResponseCodeTypes {
	
	AUTHENTICATION_SUCCESS(400L), BAD_CREDENTIALS(401L);
	
	private Long code;
	
	private AuthenticationResponseCode(Long code) {
		this.code = code;
	}

	@Override
	public Long getResponseCode() {
		return code;
	}

}
