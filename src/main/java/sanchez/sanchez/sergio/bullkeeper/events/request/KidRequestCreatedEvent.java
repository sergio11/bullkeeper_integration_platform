package sanchez.sanchez.sergio.bullkeeper.events.request;

import org.springframework.context.ApplicationEvent;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.LocationDTO;

/**
 * Kid Request Created Event
 * @author sergiosanchezsanchez
 *
 */
public final class KidRequestCreatedEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identity
	 */
	private String identity;

	/**
	 * Type
	 */
	private String type;
	
	/**
	 * Location
	 */
	private LocationDTO location;
    
	/**
	 * Kid
	 */
    private String kid;
    

    /**
     * Terminal
     */
    private String terminal;
	
    /**
     * Kid Request Created Event
     * @param source
     * @param identity
     * @param type
     * @param location
     * @param kid
     * @param terminal
     */
	public KidRequestCreatedEvent(final Object source, final String identity, final String type,
			final LocationDTO location, final String kid, final String terminal) {
		super(source);
		this.identity = identity;
		this.type = type;
		this.location = location;
		this.kid = kid;
		this.terminal = terminal;
	}
	
	

	public String getIdentity() {
		return identity;
	}



	public void setIdentity(String identity) {
		this.identity = identity;
	}



	public String getType() {
		return type;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public String getKid() {
		return kid;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	@Override
	public String toString() {
		return "KidRequestCreatedEvent [type=" + type + ", location=" + location + ", kid=" + kid + ", terminal="
				+ terminal + "]";
	}
}
