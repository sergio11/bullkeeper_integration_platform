package es.bisite.usal.bulltect.web.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sergio
 */
public class CommentsAnalyzedStatisticsDTO {
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("comments")
	private List<CommentAnalyzedDTO> data;
    
	public CommentsAnalyzedStatisticsDTO(){}
	

	public CommentsAnalyzedStatisticsDTO(String title, List<CommentAnalyzedDTO> data) {
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


	public List<CommentAnalyzedDTO> getData() {
		return data;
	}


	public void setData(List<CommentAnalyzedDTO> data) {
		this.data = data;
	}


	public static class CommentAnalyzedDTO {

		@JsonProperty("date")
        private String date;
		@JsonProperty("total")
		private int value;
		@JsonProperty("label")
		private String label;
		
		public CommentAnalyzedDTO(){}

		public CommentAnalyzedDTO(String date, int value, String label) {
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
			return "CommentAnalyzedDTO [date=" + date + ", value=" + value + ", label=" + label + "]";
		}
    }


	@Override
	public String toString() {
		return "CommentsAnalyzedStatisticsDTO [title=" + title + ", data=" + data + "]";
	}

	
}
