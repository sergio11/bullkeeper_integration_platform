package sanchez.sanchez.sergio.masoc.web.dto.response;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.masoc.persistence.entity.AlertLevelEnum;

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
        private AlertLevelEnum level;
		@JsonProperty("total")
		private long total;
		@JsonProperty("label")
		private String label;
		
		public AlertLevelDTO(){}
		
		public AlertLevelDTO(AlertLevelEnum level, long total, String label) {
			super();
			this.level = level;
			this.total = total;
			this.label = label;
		}
		
		
		public AlertLevelEnum getLevel() {
			return level;
		}
		
		public void setLevel(AlertLevelEnum level) {
			this.level = level;
		}
		
		public long getTotal() {
			return total;
		}
		
		public void setTotal(long total) {
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
