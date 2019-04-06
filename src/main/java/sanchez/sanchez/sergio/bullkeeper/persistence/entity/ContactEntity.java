package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Contact Entity
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = ContactEntity.COLLECTION_NAME)
public class ContactEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Collection Name
	 */
	public final static String COLLECTION_NAME = "contacts";

	/**
	 * App id
	 */
	@Id
	private ObjectId id;
	
	/**
	 * Name
	 */
	@Field("name")
	private String name;
	
	
	/**
	 * Photo Encoded String
	 */
	@Field("photo_encoded_string")
	private String photoEncodedString;
	
	/**
	 * Disabled
	 */
	@Field("disabled")
	private Boolean disabled = false;
	
	
	/**
	 * Local Id
	 */
	@Field("local_id")
	protected String localId;
	
	/**
	 * Phone List
	 */
	@Field("phone_list")
    protected Iterable<PhoneContactEntity> phoneList;

    /**
     * Email List
     */
	@Field("email_list")
    protected Iterable<EmailContactEntity> emailList;

    /**
     * Address List
     */
	@Field("address_list")
    protected Iterable<PostalAddressEntity> addressList;
	
	/**
	 * Kid
	 */
	@Field("kid")
    @DBRef
    private KidEntity kid;
	
	/**
	 * Terminal
	 */
	@Field("terminal")
	@DBRef
	private TerminalEntity terminal;
	
	
	
	public ContactEntity() {}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param photoEncodedString
	 * @param disabled
	 * @param localId
	 * @param phoneList
	 * @param emailList
	 * @param addressList
	 * @param kid
	 * @param terminal
	 */
	@PersistenceConstructor
	public ContactEntity(ObjectId id, String name, String photoEncodedString, Boolean disabled, String localId,
			Iterable<PhoneContactEntity> phoneList, Iterable<EmailContactEntity> emailList,
			Iterable<PostalAddressEntity> addressList, KidEntity kid, TerminalEntity terminal) {
		super();
		this.id = id;
		this.name = name;
		this.photoEncodedString = photoEncodedString;
		this.disabled = disabled;
		this.localId = localId;
		this.phoneList = phoneList;
		this.emailList = emailList;
		this.addressList = addressList;
		this.kid = kid;
		this.terminal = terminal;
	}

	

	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhotoEncodedString() {
		return photoEncodedString;
	}

	public void setPhotoEncodedString(String photoEncodedString) {
		this.photoEncodedString = photoEncodedString;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public Iterable<PhoneContactEntity> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(Iterable<PhoneContactEntity> phoneList) {
		this.phoneList = phoneList;
	}

	public Iterable<EmailContactEntity> getEmailList() {
		return emailList;
	}

	public void setEmailList(Iterable<EmailContactEntity> emailList) {
		this.emailList = emailList;
	}

	public Iterable<PostalAddressEntity> getAddressList() {
		return addressList;
	}

	public void setAddressList(Iterable<PostalAddressEntity> addressList) {
		this.addressList = addressList;
	}

	public KidEntity getKid() {
		return kid;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}

	public TerminalEntity getTerminal() {
		return terminal;
	}

	public void setTerminal(TerminalEntity terminal) {
		this.terminal = terminal;
	}

	@Override
	public String toString() {
		return "ContactEntity [id=" + id + ", name=" + name + ", photoEncodedString=" + photoEncodedString
				+ ", disabled=" + disabled + ", localId=" + localId + ", phoneList=" + phoneList + ", emailList="
				+ emailList + ", addressList=" + addressList + ", kid=" + kid + ", terminal=" + terminal + "]";
	}


	/**
	 * Phone Contact Entity
	 * @author ssanchez
	 *
	 */
	@Document
	public static class PhoneContactEntity {
		
		@Field("phone")
		private String phone;
		
		public PhoneContactEntity() {}

		public PhoneContactEntity(String phone) {
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
			return "PhoneContactEntity [phone=" + phone + "]";
		}
		
	}
	
	@Document
	public static class EmailContactEntity {
		
		/**
		 * Email
		 */
		@Field("email")
		private String email;
		
		public EmailContactEntity() {}

		public EmailContactEntity(final String email) {
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
			return "EmailContactEntity [email=" + email + "]";
		}
		
	}
	
	@Document
	public static class PostalAddressEntity {
		
		/**
         * City
         */
		@Field("city")
		private String city;

        /**
         * State
         */
		@Field("state")
		private String state;

        /**
         * Country
         */
		@Field("country")
        private String country;
		
		public PostalAddressEntity() {}

		/**
		 * 
		 * @param city
		 * @param state
		 * @param country
		 */
		public PostalAddressEntity(final String city, final String state, final String country) {
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
			return "PostalAddressEntity [city=" + city + ", state=" + state + ", country=" + country + "]";
		}

		
	}
	
}
