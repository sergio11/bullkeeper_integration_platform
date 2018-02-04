package es.bisite.usal.bulltect.web.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sergio
 */
public class CommentsStatisticsDTO {
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("comments")
	private List<CommentsPerDateDTO> data;
    
	public CommentsStatisticsDTO(){}
	

	public CommentsStatisticsDTO(String title, List<CommentsPerDateDTO> data) {
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


	public List<CommentsPerDateDTO> getData() {
		return data;
	}


	public void setData(List<CommentsPerDateDTO> data) {
		this.data = data;
	}


	public static class CommentsPerDateDTO {

		@JsonProperty("date")
        private String date;
		@JsonProperty("total")
		private Long value;
		@JsonProperty("label")
		private String label;
		
		public CommentsPerDateDTO(){}

		public CommentsPerDateDTO(String date, Long value, String label) {
			super();
			this.date = date;
			this.value = value;
			this.label = label;
		}


		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
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

		@Override
		public String toString() {
			return "CommentAnalyzedDTO [date=" + date + ", value=" + value + ", label=" + label + "]";
		}
    }


	@Override
	public String toString() {
		return "CommentsAnalyzedStatisticsDTO [title=" + title + ", data=" + data + "]";
	}

	
}
