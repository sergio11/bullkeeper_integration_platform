package es.bisite.usal.bulltect.web.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum;


public class MostActiveFriendsDTO {
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("users")
	private List<UserDTO> users;
	
	public MostActiveFriendsDTO(){}
    
	
	public MostActiveFriendsDTO(String title, List<UserDTO> users) {
		super();
		this.title = title;
		this.users = users;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public List<UserDTO> getUsers() {
		return users;
	}


	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}


	public static class UserDTO {

		@JsonProperty("external_id")
		private String id;
		@JsonProperty("name")
        private String name;
		@JsonProperty("profile_image")
        private String profileImage;
		@JsonProperty("social_media")
		private SocialMediaTypeEnum socialMedia;
		@JsonProperty("value")
		private Long value;
		@JsonProperty("value_label")
		private String valueLabel;
		
		public UserDTO(){}
	
		public UserDTO(String id, String name, String profileImage, SocialMediaTypeEnum socialMedia, Long value, String valueLabel) {
			super();
			this.id = id;
			this.name = name;
			this.profileImage = profileImage;
			this.socialMedia = socialMedia;
			this.value = value;
			this.valueLabel = valueLabel;
		}
		
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getProfileImage() {
			return profileImage;
		}

		public void setProfileImage(String profileImage) {
			this.profileImage = profileImage;
		}

		public Long getValue() {
			return value;
		}
		
		public void setValue(Long value) {
			this.value = value;
		}
		

		public SocialMediaTypeEnum getSocialMedia() {
			return socialMedia;
		}

		public void setSocialMedia(SocialMediaTypeEnum socialMedia) {
			this.socialMedia = socialMedia;
		}

		public String getValueLabel() {
			return valueLabel;
		}

		public void setValueLabel(String valueLabel) {
			this.valueLabel = valueLabel;
		}

		@Override
		public String toString() {
			return "UserDTO [name=" + name + ", profileImage=" + profileImage + ", socialMedia=" + socialMedia
					+ ", value=" + value + ", valueLabel=" + valueLabel + "]";
		}

		
    }
	
	


}
