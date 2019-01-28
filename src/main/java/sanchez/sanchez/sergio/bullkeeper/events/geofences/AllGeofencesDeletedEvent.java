package sanchez.sanchez.sergio.bullkeeper.events.geofences;

import org.springframework.context.ApplicationEvent;

/**
 * All Geofence Deleted Event
 * @author sergiosanchezsanchez
 *
 */
public final class AllGeofencesDeletedEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Kid
	 */
	private String kid;
	

	/**
	 * 
	 * @param source
	 * @param kid
	 */
	public AllGeofencesDeletedEvent(final Object source, final String kid) {
		super(source);
		this.kid = kid;
	}

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "AllGeofencesDeletedEvent [kid=" + kid + "]";
	}
}
