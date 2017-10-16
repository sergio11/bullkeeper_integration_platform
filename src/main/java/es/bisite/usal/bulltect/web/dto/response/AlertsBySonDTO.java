package es.bisite.usal.bulltect.web.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class AlertsBySonDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@JsonProperty("identity")
	private String id;
	
	@JsonProperty("alerts")
	private ArrayList<Map<String, String>> alerts;

	
	public AlertsBySonDTO() {
		super();
	}

	public AlertsBySonDTO(String id, ArrayList<Map<String, String>> alerts) {
		super();
		this.id = id;
		this.alerts = alerts;
	}

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Map<String, String>> getAlerts() {
		return alerts;
	}

	public void setAlerts(ArrayList<Map<String, String>> alerts) {
		this.alerts = alerts;
	}
}
