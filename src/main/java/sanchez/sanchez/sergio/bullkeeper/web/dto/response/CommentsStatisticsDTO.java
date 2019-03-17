package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import static sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentsStatisticsDTO.CommentsResultDTO;

/**
 * @author sergio
 */
public class CommentsStatisticsDTO<T extends CommentsResultDTO> {
	
	/**
	 * Title
	 */
	@JsonProperty("title")
	private String title;
	
	/**
	 * Subtitle
	 */
	@JsonProperty("subtitle")
	private String subtitle;
	
	/**
     * Total Comments
     */
    @JsonProperty("total_comments")
    private int totalComments;
	
	/**
	 * Data
	 */
	@JsonProperty("comments")
	private List<T> data;
    
	public CommentsStatisticsDTO(){}
	
	/**
	 * 
	 * @param title
	 * @param subtitle
	 * @param totalComments
	 * @param data
	 */
	public CommentsStatisticsDTO(final String title, final String subtitle, final int totalComments, final List<T> data) {
		super();
		this.title = title;
		this.subtitle = subtitle;
		this.totalComments = totalComments;
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

	public int getTotalComments() {
		return totalComments;
	}

	public void setTotalComments(int totalComments) {
		this.totalComments = totalComments;
	}

	public List<T> getData() {
		return data;
	}


	public void setData(List<T> data) {
		this.data = data;
	}
	
	
	/**
	 * Comments Result DTO
	 * @author ssanchez
	 *
	 */
	public static abstract class CommentsResultDTO {
		
		@JsonProperty("total")
		private Long value;
		@JsonProperty("label")
		private String label;
		
		public CommentsResultDTO() {}
		
		/**
		 
		 * @param value
		 * @param label
		 */
		public CommentsResultDTO(final Long value, final String label) {
			super();
			this.value = value;
			this.label = label;
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
			return "CommentsResultDTO [value=" + value + ", label=" + label + "]";
		}
	}

	/**
	 * Comments Per Date DTO
	 * @author ssanchez
	 *
	 */
	public static class CommentsPerDateDTO extends CommentsResultDTO {

		@JsonProperty("date")
        private String date;
		
		public CommentsPerDateDTO() {}

		/**
		 * 
		 * @param value
		 * @param label
		 * @param date
		 */
		public CommentsPerDateDTO(Long value, String label, String date) {
			super(value, label);
			this.date = date;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		@Override
		public String toString() {
			return "CommentsPerDateDTO [date=" + date + "]";
		}
		
    }
	
	/**
	 * Comments Per Social Media
	 * @author ssanchez
	 *
	 */
	public static class CommentsPerSocialMediaDTO extends CommentsResultDTO {

		@JsonProperty("social_media")
        private String socialMedia;
		
		public CommentsPerSocialMediaDTO() {}

		/**
		 * @param value
		 * @param label
		 * @param socialMedia
		 */
		public CommentsPerSocialMediaDTO(Long value, String label, String socialMedia) {
			super(value, label);
			this.socialMedia = socialMedia;
		}

		

		public String getSocialMedia() {
			return socialMedia;
		}

		public void setSocialMedia(String socialMedia) {
			this.socialMedia = socialMedia;
		}

		@Override
		public String toString() {
			return "CommentsPerSocialMediaDTO [socialMedia=" + socialMedia + "]";
		}
		
    }


	@Override
	public String toString() {
		return "CommentsStatisticsDTO [title=" + title + ", data=" + data + "]";
	}
	
	
}
