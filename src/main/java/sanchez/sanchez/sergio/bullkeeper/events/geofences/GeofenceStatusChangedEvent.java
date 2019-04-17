package sanchez.sanchez.sergio.bullkeeper.events.geofences;

import org.springframework.context.ApplicationEvent;

/**
 * Geofence Status Changed Event
 * @author sergiosanchezsanchez
 *
 */
public final class GeofenceStatusChangedEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identity
	 */
	private String identity;

	/**
	 * Kid
	 */
	private String kid;
	
	
	/**
	 * Is Enabled
	 */
	private Boolean isEnabled;

	
	/**
	 * 
	 * @param source
	 * @param identity
	 * @param kid
	 * @param isEnabled
	 */
	public GeofenceStatusChangedEvent(Object source, String identity, String kid,
			Boolean isEnabled) {
		super(source);
		this.identity = identity;
		this.kid = kid;
		this.isEnabled = isEnabled;
	}


	public String getIdentity() {
		return identity;
	}


	public void setIdentity(String identity) {
		this.identity = identity;
	}


	public String getKid() {
		return kid;
	}


	public void setKid(String kid) {
		this.kid = kid;
	}


	public Boolean getIsEnabled() {
		return isEnabled;
	}


	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}


	@Override
	public String toString() {
		return "GeofenceStatusChangedEvent [identity=" + identity + ", kid=" + kid + ", isEnabled=" + isEnabled + "]";
	}
}
