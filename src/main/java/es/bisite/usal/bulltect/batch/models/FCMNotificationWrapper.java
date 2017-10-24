package es.bisite.usal.bulltect.batch.models;

import java.util.List;

import org.bson.types.ObjectId;

import es.bisite.usal.bulltect.fcm.operations.FCMNotificationOperation;

public class FCMNotificationWrapper {
	
	private FCMNotificationOperation fcmNotificationOperation;
	private List<ObjectId> alertIds;
	

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
