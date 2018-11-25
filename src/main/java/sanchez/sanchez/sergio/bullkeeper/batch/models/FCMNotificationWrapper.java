package sanchez.sanchez.sergio.bullkeeper.batch.models;

import java.util.List;
import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.fcm.operations.FCMNotificationOperation;

/**
 * FCM Notification Wrapper
 * @author sergiosanchezsanchez
 *
 */
public class FCMNotificationWrapper {
	
	private FCMNotificationOperation fcmNotificationOperation;
	private List<ObjectId> alertIds;
	
	/**
	 * 
	 * @param fcmNotificationOperation
	 * @param alertIds
	 */
	public FCMNotificationWrapper(FCMNotificationOperation fcmNotificationOperation, List<ObjectId> alertIds) {
		super();
		this.fcmNotificationOperation = fcmNotificationOperation;
		this.alertIds = alertIds;
	}

	public FCMNotificationOperation getFcmNotificationOperation() {
		return fcmNotificationOperation;
	}
	
	public void setFcmNotificationOperation(FCMNotificationOperation fcmNotificationOperation) {
		this.fcmNotificationOperation = fcmNotificationOperation;
	}
	
	public List<ObjectId> getAlertIds() {
		return alertIds;
	}
	
	public void setAlertIds(List<ObjectId> alertIds) {
		this.alertIds = alertIds;
	}

}
