package es.bisite.usal.bulltect.batch.models;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import es.bisite.usal.bulltect.persistence.entity.AlertEntity;

public class AlertsByParent {
	
	private ObjectId parentId;
	private List<AlertEntity> alerts;
	
	
	public AlertsByParent(ObjectId parentId, List<AlertEntity> alerts) {
		super();
		this.parentId = parentId;
		this.alerts = alerts;
	}


	public ObjectId getParentId() {
		return parentId;
	}


	public void setParentId(ObjectId parentId) {
		this.parentId = parentId;
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
