package sanchez.sanchez.sergio.bullkeeper.events.photos;

import org.springframework.context.ApplicationEvent;

/**
 * Device Photo Disabled Event Event
 * @author sergiosanchezsanchez
 */
public final class DevicePhotoDisabledEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Kid
	 */
	private String kid;
	
	/**
	 * Terminal
	 */
	private String terminal;
	
	
	/**
	 * Photo
	 */
	private String photo;
	
	/**
	 * Local Id
	 */
	private String localId;
	

	/**
	 * @param source
	 * @param kid
	 * @param terminal
	 * @param photo
	 * @param localId
	 */
	public DevicePhotoDisabledEvent(final Object source,
			final String kid, final String terminal,
			final String photo, final String localId) {
		super(source);
		this.kid = kid;
		this.terminal = terminal;
		this.photo = photo;
		this.localId = localId;
	}


	public String getKid() {
		return kid;
	}


	public void setKid(String kid) {
		this.kid = kid;
	}


	public String getTerminal() {
		return terminal;
	}


	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public String getLocalId() {
		return localId;
	}


	public void setLocalId(String localId) {
		this.localId = localId;
	}


	@Override
	public String toString() {
		return "DevicePhotoDisabledEvent [kid=" + kid + ", terminal=" + terminal + ", photo=" + photo + ", localId="
				+ localId + "]";
	}


	
}
