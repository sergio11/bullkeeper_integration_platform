package es.bisite.usal.bulltect.web.dto.response;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum;

/**
 * @author sergio
 */
public class SocialMediaActivityStatisticsDTO {
    
    
    
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("activities")
	private List<ActivityDTO> data;
	
	public SocialMediaActivityStatisticsDTO(){}

	
	
	public SocialMediaActivityStatisticsDTO(String title, List<ActivityDTO> data) {
		super();
		this.title = title;
		this.data = data;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public List<ActivityDTO> getData() {
		return data;
	}



	public void setData(List<ActivityDTO> data) {
		this.data = data;
	}



	public static class ActivityDTO {

		@JsonProperty("type")
        public String type;
		@JsonProperty("value")
        public int value;
		@JsonProperty("label")
		public String label;
		
		public ActivityDTO(String type, int value, String label) {
			super();
			this.type = type;
			this.value = value;
			this.label = label;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return "ActivityDTO [type=" + type + ", value=" + value + ", label=" + label + "]";
		}
		
		
    }
	

	@Override
	public String toString() {
		return "SocialMediaActivityStatisticsDTO [title=" + title + ", data=" + data + "]";
	}

    
    
}
