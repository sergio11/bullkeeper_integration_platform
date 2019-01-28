package sanchez.sanchez.sergio.bullkeeper.events.geofences;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEvent;

/**
 * Geofences Deleted Event
 * @author sergiosanchezsanchez
 *
 */
public final class GeofencesDeletedEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Ids
	 */
	private List<ObjectId> ids;
	
	/**
	 * Kid
	 */
	private String kid;
	

	/**
	 * Geofence Deleted Event
	 * @param source
	 * @param ids
	 * @param kid
	 */
	public GeofencesDeletedEvent(final Object source, final List<ObjectId> ids, final String kid) {
		super(source);
		this.ids = ids;
		this.kid = kid;
	}


	public List<ObjectId> getIds() {
		return ids;
	}

	public String getKid() {
		return kid;
	}

	public void setIds(List<ObjectId> ids) {
		this.ids = ids;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}


	@Override
	public String toString() {
		return "GeofencesDeletedEvent [ids=" + ids + ", kid=" + kid + "]";
	}

	
    
}
