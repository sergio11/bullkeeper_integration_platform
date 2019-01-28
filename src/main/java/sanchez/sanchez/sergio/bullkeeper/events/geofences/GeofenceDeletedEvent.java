package sanchez.sanchez.sergio.bullkeeper.events.geofences;

import org.springframework.context.ApplicationEvent;

/**
 * Geofence Added Event
 * @author sergiosanchezsanchez
 *
 */
public final class GeofenceDeletedEvent extends ApplicationEvent {
	
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
	 * 
	 * @param source
	 * @param identity
	 * @param kid
	 */
	public GeofenceDeletedEvent(final Object source, final String identity, final String kid) {
		super(source);
		this.identity = identity;
		this.kid = kid;
	}


	public String getIdentity() {
		return identity;
	}

	public String getKid() {
		return kid;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}


	@Override
	public String toString() {
		return "GeofenceDeletedEvent [identity=" + identity + ", kid=" + kid + "]";
	}
    
}
