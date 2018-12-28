package sanchez.sanchez.sergio.bullkeeper.events.location;

import org.springframework.context.ApplicationEvent;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.LocationDTO;

/**
 * Current Location Update Event
 * @author sergiosanchezsanchez
 *
 */
public final class CurrentLocationUpdateEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Kid
	 */
	private final String kid;
	
	/**
	 * Location
	 */
	private final LocationDTO location;
	

	/**
	 * 
	 * @param source
	 * @param kid
	 * @param location
	 */
	public CurrentLocationUpdateEvent(Object source, final String kid,
			final LocationDTO location) {
		super(source);
		this.kid = kid;
		this.location = location;
	}


	public String getKid() {
		return kid;
	}


	public LocationDTO getLocation() {
		return location;
	}


	@Override
	public String toString() {
		return "CurrentLocationUpdateEvent [kid=" + kid + ", location=" + location + "]";
	}

}
