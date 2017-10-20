package es.bisite.usal.bulltect.web.dto.response;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sergio
 */
public class DimensionsStatisticsDTO {
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("dimensions")
	private List<DimensionDTO> data;
    
	public DimensionsStatisticsDTO(){}
	
    public DimensionsStatisticsDTO(String title, List<DimensionDTO> data) {
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

	public List<DimensionDTO> getData() {
		return data;
	}

	public void setData(List<DimensionDTO> data) {
		this.data = data;
	}


	public static class DimensionDTO {

		@JsonProperty("type")
        private String type;
		@JsonProperty("value")
		private int value;
		@JsonProperty("label")
		private String label;
		
		public DimensionDTO(){}
		
		public DimensionDTO(String type, int value, String label) {
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

    }
	

	@Override
	public String toString() {
		return "DimensionsStatisticsDTO [title=" + title + ", data=" + data + "]";
	}
}
