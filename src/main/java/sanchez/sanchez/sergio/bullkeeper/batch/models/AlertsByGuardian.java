package sanchez.sanchez.sergio.bullkeeper.batch.models;

import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertEntity;

/**
 * Alerts By Guardian
 * @author sergiosanchezsanchez
 *
 */
public class AlertsByGuardian {
	
	/**
	 * Guardian
	 */
	private ObjectId guardian;
	
	/**
	 * Alerts
	 */
	private List<AlertEntity> alerts;
	
	/**
	 * 
	 * @param guardian
	 * @param alerts
	 */
	public AlertsByGuardian(final ObjectId guardian, final List<AlertEntity> alerts) {
		super();
		this.guardian = guardian;
		this.alerts = alerts;
	}

	public ObjectId getGuardian() {
		return guardian;
	}

	public void setGuardian(final ObjectId guardian) {
		this.guardian = guardian;
	}

	public List<AlertEntity> getAlerts() {
		return alerts;
	}
	
	public List<ObjectId> getAlertIds(){
		return alerts.stream().map((alert) -> alert.getId()).collect(Collectors.toList());
	}


	public void setAlerts(List<AlertEntity> alerts) {
		this.alerts = alerts;
	}

}
