package sanchez.sanchez.sergio.bullkeeper.fcm.operations;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FCMNotificationOperation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("to")
    private String notificationKey;
	@JsonProperty("notification")
	private FCMNotification notification;
	

	public FCMNotificationOperation(){}
	
	
	public FCMNotificationOperation(String notificationKey, FCMNotification notification) {
		super();
		this.notificationKey = notificationKey;
		this.notification = notification;
	}


	public String getNotificationKey() {
		return notificationKey;
	}

	public void setNotificationKey(String notificationKey) {
		this.notificationKey = notificationKey;
	}

	
	public FCMNotification getNotification() {
		return notification;
	}


	public void setNotification(FCMNotification notification) {
		this.notification = notification;
	}


	public static class FCMNotification {
		
		@JsonProperty("title")
		private String title;
		@JsonProperty("body")
		private String body;
		
		public FCMNotification(){}
		
		public FCMNotification(String title, String body) {
			super();
			this.title = title;
			this.body = body;
		}

		public String getTitle() {
			return title;
		}
		
		public void setTitle(String title) {
			this.title = title;
		}
		
		public String getBody() {
			return body;
		}
		
		public void setBody(String body) {
			this.body = body;
		}
		
	}
}
