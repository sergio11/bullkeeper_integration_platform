package es.bisite.usal.bulltect.web.dto.response;

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

		@JsonProperty("name")
        private String name;
		@JsonProperty("value")
		private int value;
		
		public UserDTO(){}
	
		public UserDTO(String name, int value) {
			super();
			this.name = name;
			this.value = value;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "UserDTO [name=" + name + ", value=" + value + "]";
		}
		
    }
	
	


}
