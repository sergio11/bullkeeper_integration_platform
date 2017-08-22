package sanchez.sanchez.sergio.fcm.properties;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FCMCustomProperties implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Value("${fcm.app.server.key}")
	private String appServerKey;
	@Value("${fcm.notification.groups.url}")
    private String notificationGroupsUrl;
	@Value("${fcm.notification.send.url}")
    private String notificationSendUrl;
	@Value("${fcm.sender.id}")
	private String senderId;
	@Value("${fcm.app.group.prefix}")
	private String groupPrefix;

    public String getAppServerKey() {
        return appServerKey;
    }

    public void setAppServerKey(String appServerKey) {
        this.appServerKey = appServerKey;
    }

    public String getNotificationGroupsUrl() {
        return notificationGroupsUrl;
    }

    public void setNotificationGroupsUrl(String notificationGroupsUrl) {
        this.notificationGroupsUrl = notificationGroupsUrl;
    }

    public String getNotificationSendUrl() {
        return notificationSendUrl;
    }

    public void setNotificationSendUrl(String notificationSendUrl) {
        this.notificationSendUrl = notificationSendUrl;
    }

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getGroupPrefix() {
		return groupPrefix;
	}

	public void setGroupPrefix(String groupPrefix) {
		this.groupPrefix = groupPrefix;
	}
}
