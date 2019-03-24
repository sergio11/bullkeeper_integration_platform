package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sergio
 */
public class DimensionsStatisticsDTO {
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("subtitle")
	private String subtitle;
	
	@JsonProperty("dimensions")
	private List<DimensionDTO> data;
    
	public DimensionsStatisticsDTO(){}
	
    public DimensionsStatisticsDTO(String title, String subtitle, List<DimensionDTO> data) {
		super();
		this.title = title;
		this.subtitle = subtitle;
		this.data = data;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
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
		private Long value;
		@JsonProperty("label")
		private String label;
		
		public DimensionDTO(){}
		
		public DimensionDTO(String type, Long value, String label) {
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

		public Long getValue() {
			return value;
		}

		public void setValue(Long value) {
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
		return "DimensionsStatisticsDTO [title=" + title + ", subtitle=" + subtitle + ", data=" + data + "]";
	}
	

	
}
