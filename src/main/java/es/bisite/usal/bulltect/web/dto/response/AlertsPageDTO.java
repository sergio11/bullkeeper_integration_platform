package es.bisite.usal.bulltect.web.dto.response;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlertsPageDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("alerts")
	private Iterable<AlertDTO> alerts;
	@JsonProperty("total")
	private Integer total;
	@JsonProperty("returned")
	private Integer returned;
	@JsonProperty("remaining")
	private Integer remaining;
	@JsonProperty("last_query")
	private Date lastQuery;
	
	public AlertsPageDTO(){}

	public AlertsPageDTO(Iterable<AlertDTO> alerts, Integer total, Integer returned, Integer remaining,
			Date lastQuery) {
		super();
		this.alerts = alerts;
		this.total = total;
		this.returned = returned;
		this.remaining = remaining;
		this.lastQuery = lastQuery;
	}

	public Iterable<AlertDTO> getAlerts() {
		return alerts;
	}

	public void setAlerts(Iterable<AlertDTO> alerts) {
		this.alerts = alerts;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getReturned() {
		return returned;
	}

	public void setReturned(Integer returned) {
		this.returned = returned;
	}

	public Integer getRemaining() {
		return remaining;
	}

	public void setRemaining(Integer remaining) {
		this.remaining = remaining;
	}

	public Date getLastQuery() {
		return lastQuery;
	}

	public void setLastQuery(Date lastQuery) {
		this.lastQuery = lastQuery;
	}

	
	
}
