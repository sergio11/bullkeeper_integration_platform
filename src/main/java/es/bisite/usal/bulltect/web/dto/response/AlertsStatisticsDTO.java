package es.bisite.usal.bulltect.web.dto.response;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class AlertsStatisticsDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("alerts")
	private List<AlertLevelDTO> alerts;

	
	public AlertsStatisticsDTO() {
		super();
	}
	

	public AlertsStatisticsDTO(String title, List<AlertLevelDTO> alerts) {
		super();
		this.title = title;
		this.alerts = alerts;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public List<AlertLevelDTO> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<AlertLevelDTO> alerts) {
		this.alerts = alerts;
	}

	public static class AlertLevelDTO {
		
		@JsonProperty("level")
        private String level;
		@JsonProperty("total")
		private int total;
		@JsonProperty("label")
		private String label;
		
		public AlertLevelDTO(){}
		
		public AlertLevelDTO(String level, int total, String label) {
			super();
			this.level = level;
			this.total = total;
			this.label = label;
		}
		
		
		public String getLevel() {
			return level;
		}
		
		public void setLevel(String level) {
			this.level = level;
		}
		
		public int getTotal() {
			return total;
		}
		
		public void setTotal(int total) {
			this.total = total;
		}
		
		public String getLabel() {
			return label;
		}
		
		public void setLabel(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return "AlertLevelDTO [level=" + level + ", total=" + total + ", label=" + label + "]";
		}
	
	}
}
