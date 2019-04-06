package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.ClearStringNoSpacesDeserializer;

/**
 * Save Contact DTO
 * @author sergiosanchezsanchez
 *
 */
public final class SaveContactDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Name
	 */
	@JsonProperty("name")
	private String name;
	
	
	/**
	 * Local Id
	 */
	@JsonProperty("local_id")
	protected String localId;
	
	/**
	 * Photo Encoded String
	 */
	@JsonProperty("photo_encoded_string")
	protected String photoEncodedString;
	
	/**
     * Phone List
     */
	@JsonProperty("phone_list")
    protected List<SavePhoneContactDTO> phoneList;

    /**
     * Email List
     */
	@JsonProperty("email_list")
    protected List<SaveEmailContactDTO> emailList;

    /**
     * Address List
     */
	@JsonProperty("address_list")
    protected List<SavePostalAddressDTO> addressList;
	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Terminal
	 */
	@JsonProperty("terminal")
	private String terminal;
	
	
	public SaveContactDTO() {}

	
	public SaveContactDTO(String name, String localId, String photoEncodedString, List<SavePhoneContactDTO> phoneList,
			List<SaveEmailContactDTO> emailList, List<SavePostalAddressDTO> addressList, String kid, String terminal) {
		super();
		this.name = name;
		this.localId = localId;
		this.photoEncodedString = photoEncodedString;
		this.phoneList = phoneList;
		this.emailList = emailList;
		this.addressList = addressList;
		this.kid = kid;
		this.terminal = terminal;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLocalId() {
		return localId;
	}


	public void setLocalId(String localId) {
		this.localId = localId;
	}


	public String getPhotoEncodedString() {
		return photoEncodedString;
	}


	public void setPhotoEncodedString(String photoEncodedString) {
		this.photoEncodedString = photoEncodedString;
	}


	public List<SavePhoneContactDTO> getPhoneList() {
		return phoneList;
	}


	public void setPhoneList(List<SavePhoneContactDTO> phoneList) {
		this.phoneList = phoneList;
	}


	public List<SaveEmailContactDTO> getEmailList() {
		return emailList;
	}


	public void setEmailList(List<SaveEmailContactDTO> emailList) {
		this.emailList = emailList;
	}


	public List<SavePostalAddressDTO> getAddressList() {
		return addressList;
	}


	public void setAddressList(List<SavePostalAddressDTO> addressList) {
		this.addressList = addressList;
	}


	public String getKid() {
		return kid;
	}


	public void setKid(String kid) {
		this.kid = kid;
	}


	public String getTerminal() {
		return terminal;
	}


	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}



	@Override
	public String toString() {
		return "SaveContactDTO [name=" + name + ", localId=" + localId + ", photoEncodedString=" + photoEncodedString
				+ ", phoneList=" + phoneList + ", emailList=" + emailList + ", addressList=" + addressList + ", kid="
				+ kid + ", terminal=" + terminal + "]";
	}



	/**
	 * Save Phone Contact
	 * @author ssanchez
	 *
	 */
	public static class SavePhoneContactDTO {
		
		@JsonProperty("phone")
		@JsonDeserialize(using = ClearStringNoSpacesDeserializer.class)
		private String phone;
		
		public SavePhoneContactDTO() {}

		public SavePhoneContactDTO(String phone) {
			super();
			this.phone = phone;
		}

		

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		@Override
		public String toString() {
			return "SavePhoneContactDTO [phone=" + phone + "]";
		}
		
	}
	
	public static class SaveEmailContactDTO {
		
		/**
		 * Email
		 */
		@JsonProperty("email")
		private String email;
		
		public SaveEmailContactDTO() {}

		public SaveEmailContactDTO(final String email) {
			super();
			this.email = email;
		}

		

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		@Override
		public String toString() {
			return "SaveEmailContactDTO [email=" + email + "]";
		}
		
	}
	
	public static class SavePostalAddressDTO {
		
		/**
         * City
         */
		@JsonProperty("city")
		private String city;

        /**
         * State
         */
		@JsonProperty("state")
		private String state;

        /**
         * Country
         */
		@JsonProperty("country")
        private String country;
		
		public SavePostalAddressDTO() {}

		/**
		 * 
		 * @param city
		 * @param state
		 * @param country
		 */
		public SavePostalAddressDTO(final String city, final String state, final String country) {
			super();
			this.city = city;
			this.state = state;
			this.country = country;
		}

		

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		@Override
		public String toString() {
			return "SavePostalAddressDTO [city=" + city + ", state=" + state + ", country=" + country + "]";
		}
	}
	
	
}
