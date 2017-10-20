package es.bisite.usal.bulltect.web.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sergio
 */
public class CommunitiesStatisticsDTO {
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("communities")
	private List<CommunityDTO> data;
    
	public CommunitiesStatisticsDTO(){}
	
	public CommunitiesStatisticsDTO(String title, List<CommunityDTO> data) {
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

	public List<CommunityDTO> getData() {
		return data;
	}

	public void setData(List<CommunityDTO> data) {
		this.data = data;
	}

	public static class CommunityDTO {

		@JsonProperty("type")
        private String type;
		@JsonProperty("value")
		private int value;
		@JsonProperty("label")
		private String label;
		
		public CommunityDTO(){}
		
		
		public CommunityDTO(String type, int value, String label) {
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
			return "CommunityDTO [type=" + type + ", value=" + value + ", label=" + label + "]";
		}
    }


	@Override
	public String toString() {
		return "SentimentAnalysisStatisticsDTO [title=" + title + ", data=" + data + "]";
	}
}
