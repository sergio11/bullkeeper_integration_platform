package sanchez.sanchez.sergio.masoc.web.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class NewFriendsDTO {
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("users")
	private List<UserDTO> users;
	
	public NewFriendsDTO(){}
    
	
	public NewFriendsDTO(String title, List<UserDTO> users) {
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
		@JsonProperty("since")
		private String since;
		
		public UserDTO(){}
		
		public UserDTO(String id, String name, String profileImage, String since) {
			super();
			this.id = id;
			this.name = name;
			this.profileImage = profileImage;
			this.since = since;
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

	
		public String getSince() {
			return since;
		}

		public void setSince(String since) {
			this.since = since;
		}

		@Override
		public String toString() {
			return "UserDTO [name=" + name + ", profileImage=" + profileImage + ", since=" + since + "]";
		}
		
    }
	
	


}
