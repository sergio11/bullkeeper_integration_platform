package sanchez.sanchez.sergio.fcm.operations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FCMNotificationOperation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("to")
    private String deviceGroup;
	@JsonProperty("data")
	private Map<String, String> data;
	
	public FCMNotificationOperation(){}
	
	public FCMNotificationOperation(String deviceGroup, Map<String, String> data) {
		super();
		this.deviceGroup = deviceGroup;
		this.data = data;
	}

	public String getDeviceGroup() {
		return deviceGroup;
	}
	
	public void setDeviceGroup(String deviceGroup) {
		this.deviceGroup = deviceGroup;
	}
	
	public Map<String, String> getData() {
		return data;
	}
	
	public void setData(Map<String, String> data) {
		this.data = data;
	}
}
