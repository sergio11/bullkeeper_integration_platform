package sanchez.sanchez.sergio.bullkeeper.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ContactEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ContactEntity.EmailContactEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ContactEntity.PhoneContactEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ContactEntity.PostalAddressEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.PhoneNumberBlockedRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveContactDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveContactDTO.SaveEmailContactDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveContactDTO.SavePhoneContactDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveContactDTO.SavePostalAddressDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ContactDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ContactDTO.EmailContactDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ContactDTO.PhoneContactDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ContactDTO.PostalAddressDTO;

/**
 * Contact Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class ContactEntityMapper {
	

	/**
	 * Kid Repository
	 */
	@Autowired
	protected KidRepository kidRepository;
	
	/**
	 * Terminal Repository
	 */
	@Autowired
	protected TerminalRepository terminalRepository;
	
	/**
	 * Phone Number Blocked Repository
	 */
	@Autowired
	protected PhoneNumberBlockedRepository phoneNumberBlockedRepository;
	
	/**
	 * Contact Is Blocked
	 * @param contactEntity
	 */
	protected boolean contactIsBlocked(final ContactEntity contactEntity) {
		
		final List<String> phoneNumbersList = new ArrayList<>();
		
		for(final PhoneContactEntity phoneContactEntity: contactEntity.getPhoneList()) {
			phoneNumbersList.add(phoneContactEntity.getPhone());
		}
		
		return phoneNumberBlockedRepository.countByNumberInOrPhoneNumberInAndKidIdAndTerminalId(
				phoneNumbersList, phoneNumbersList, contactEntity.getKid().getId(),
				contactEntity.getTerminal().getId()) > 0 ? true: false;
	}
	
	/**
	 * @param contactEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(contactEntity.getId().toString())", 
        		target = "identity" ),
        @Mapping(expression="java(contactEntity.getKid().getId().toString())", 
        		target = "kid" ),
        @Mapping(expression="java(contactEntity.getTerminal().getId().toString())",
        		target = "terminal" ),
        @Mapping(expression = "java(contactIsBlocked(contactEntity))",
        		target = "blocked"),
        @Mapping(expression="java(contactEntity.getPhoneList())",
			target = "phoneList" ),
        @Mapping(expression="java(contactEntity.getEmailList())",
			target = "emailList" ),
        @Mapping(expression="java(contactEntity.getAddressList())",
			target = "addressList" ),
    })
    @Named("contactEntityToContactDTO")
    public abstract ContactDTO contactEntityToContactDTO(final ContactEntity contactEntity); 
	
    /**
     * 
     * @param contactEntitiesList
     * @return
     */
    @IterableMapping(qualifiedByName = "contactEntityToContactDTO")
    public abstract Iterable<ContactDTO> contactEntityToContactDTOs(
    		Iterable<ContactEntity> contactEntitiesList);
    
    
    /**
     * 
     * @param saveContactDTO
     * @return
     */
    @Mappings({
    	@Mapping(expression="java(kidRepository.findOne(new org.bson.types.ObjectId(saveContactDTO.getKid())))", 
    		target="kid"),
    	@Mapping(expression="java(terminalRepository.findOne(new org.bson.types.ObjectId(saveContactDTO.getTerminal())))", 
    		target="terminal"),
    	@Mapping(expression="java(savePhoneContactDTOToPhoneContactEntities(saveContactDTO.getPhoneList()))", 
			target="phoneList"),
    	@Mapping(expression="java(saveEmailContactDTOToEmailContactEntities(saveContactDTO.getEmailList()))", 
			target="emailList"),
    	@Mapping(expression="java(savePostalAddressDTOToPostalAddressEntities(saveContactDTO.getAddressList()))", 
			target="addressList")
    })
    @Named("saveContactDTOToContactEntity")
    public abstract ContactEntity saveContactDTOToContactEntity(final SaveContactDTO saveContactDTO);
    
    
    /**
     * 
     * @param savePhoneContactDTO
     * @return
     */
    @Mappings({})
    @Named("savePhoneContactDTOToPhoneContactEntity")
    public abstract PhoneContactEntity savePhoneContactDTOToPhoneContactEntity(final SavePhoneContactDTO savePhoneContactDTO);
    
    /**
     * 
     * @param savePhoneContactDTOs
     * @return
     */
    @IterableMapping(qualifiedByName = "savePhoneContactDTOToPhoneContactEntity")
    public abstract Iterable<PhoneContactEntity> savePhoneContactDTOToPhoneContactEntities(final Iterable<SavePhoneContactDTO> savePhoneContactDTOs);
    
    /**
     * 
     * @param saveEmailContactDTO
     * @return
     */
    @Mappings({})
    @Named("saveEmailContactDTOToEmailContactEntity")
    public abstract EmailContactEntity saveEmailContactDTOToEmailContactEntity(final SaveEmailContactDTO saveEmailContactDTO);
    
    /**
     * 
     * @param saveEmailContactDTOs
     * @return
     */
    @IterableMapping(qualifiedByName = "saveEmailContactDTOToEmailContactEntity")
    public abstract Iterable<EmailContactEntity> saveEmailContactDTOToEmailContactEntities(final Iterable<SaveEmailContactDTO> saveEmailContactDTOs);
    
    /**
     * 
     * @param savePostalAddressDTO
     * @return
     */
    @Mappings({})
    @Named("savePostalAddressDTOToPostalAddressEntity")
    public abstract PostalAddressEntity savePostalAddressDTOToPostalAddressEntity(final SavePostalAddressDTO savePostalAddressDTO);
    
    
    /**
     * 
     * @param savePostalAddressDTOs
     * @return
     */
    @IterableMapping(qualifiedByName = "savePostalAddressDTOToPostalAddressEntity")
    public abstract Iterable<PostalAddressEntity> savePostalAddressDTOToPostalAddressEntities(final Iterable<SavePostalAddressDTO> savePostalAddressDTOs);
    
    
    /**
     * 
     * @param phoneContactEntity
     * @return
     */
    @Mappings({})
    @Named("phoneContactEntityToPhoneContactDTO")
    public abstract PhoneContactDTO phoneContactEntityToPhoneContactDTO(final PhoneContactEntity phoneContactEntity);
    
    /**
     * 
     * @param phoneContactEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "phoneContactEntityToPhoneContactDTO")
    public abstract Iterable<PhoneContactDTO> phoneContactEntitiesToPhoneContactDTOs(final Iterable<PhoneContactEntity> phoneContactEntities);
    
    /**
     * 
     * @param emailContactEntity
     * @return
     */
    @Mappings({})
    @Named("emailContactEntityToEmailContactDTO")
    public abstract EmailContactDTO emailContactEntityToEmailContactDTO(final EmailContactEntity emailContactEntity);
    
    /**
     * 
     * @param emailContactEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "emailContactEntityToEmailContactDTO")
    public abstract Iterable<EmailContactDTO> emailContactEntityToEmailContactDTOs(final Iterable<EmailContactEntity> emailContactEntities);
    
    /**
     * 
     * @param postalAddressEntity
     * @return
     */
    @Mappings({})
    @Named("postalAddressEntityToPostalAddressDTO")
    public abstract PostalAddressDTO postalAddressEntityToPostalAddressDTO(final PostalAddressEntity postalAddressEntity);
    
    
    /**
     * 
     * @param postalAddressEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "postalAddressEntityToPostalAddressDTO")
    public abstract Iterable<PostalAddressDTO> postalAddressEntitiesToPostalAddressDTOs(final Iterable<PostalAddressEntity> postalAddressEntities);
    
    /**
     * 
     * @param saveContactDTOs
     * @return
     */
    @IterableMapping(qualifiedByName = "saveContactDTOToContactEntity")
    public abstract Iterable<ContactEntity> saveContactDTOToContactEntities(final Iterable<SaveContactDTO> saveContactDTOs);

}
