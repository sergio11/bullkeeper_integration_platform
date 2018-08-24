package sanchez.sanchez.sergio.masoc.web.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sergio
 */
public class SocialMediaLikesStatisticsDTO {
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("subtitle")
	private String subtitle;
	
	@JsonProperty("likes")
	private List<SocialMediaLikesDTO> data;
    
	public SocialMediaLikesStatisticsDTO(){}
	

	public SocialMediaLikesStatisticsDTO(String title, String subtitle, List<SocialMediaLikesDTO> data) {
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


	public List<SocialMediaLikesDTO> getData() {
		return data;
	}


	public void setData(List<SocialMediaLikesDTO> data) {
		this.data = data;
	}


	public static class SocialMediaLikesDTO {

		@JsonProperty("type")
        private String type;
		@JsonProperty("likes")
		private long likes;
		@JsonProperty("label")
		private String label;
		
		public SocialMediaLikesDTO(){}
		
		public SocialMediaLikesDTO(String type, long likes, String label) {
			super();
			this.type = type;
			this.likes = likes;
			this.label = label;
		}
		
		public String getType() {
			return type;
		}
		
		public void setType(String type) {
			this.type = type;
		}
		
		public long getLikes() {
			return likes;
		}
		
		public void setLikes(long likes) {
			this.likes = likes;
		}
		
		public String getLabel() {
			return label;
		}
		
		public void setLabel(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return "SocialMediaLikesDTO [type=" + type + ", likes=" + likes + ", label=" + label + "]";
		}
		
    }


	@Override
	public String toString() {
		return "SocialMediaLikesStatisticsDTO [title=" + title + ", subtitle=" + subtitle + ", data=" + data + "]";
	}


	

	
}
