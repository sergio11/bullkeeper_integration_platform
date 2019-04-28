package sanchez.sanchez.sergio.bullkeeper.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AuthorityRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;
import sanchez.sanchez.sergio.bullkeeper.util.Utils;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianByFacebookDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianByGoogleDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.GuardianDTO;


/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class GuardianEntityMapper {
	
	/**
	 * Password Encoder
	 */
	@Autowired
    protected PasswordEncoder passwordEncoder;
	
	/**
	 * Kid Repository
	 */
	@Autowired
	protected KidRepository kidRepository;
	
	/**
	 * Authority Repository
	 */
	@Autowired
	protected AuthorityRepository authorityRepository;
	
	/**
	 * Supervised Children Repository
	 */
	@Autowired
	protected SupervisedChildrenRepository supervisedChildrenRepository;
    
	/**
	 * 
	 * @param guardianEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(guardianEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(supervisedChildrenRepository.countByGuardianId(guardianEntity.getId()))", 
        	target = "children" ),
        @Mapping(source = "guardianEntity.birthdate", target = "birthdate", dateFormat = "yyyy/MM/dd"),
        @Mapping(source = "guardianEntity.age", target = "age"),
        @Mapping(expression="java(guardianEntity.getLocale().toString())", target = "locale" ),
        @Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.util.Utils.getPhonePrefix(guardianEntity.getTelephone()))", 
        	target = "phonePrefix" ),
        @Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.util.Utils.getPhoneNumber(guardianEntity.getTelephone()))", 
        	target = "phoneNumber" )
    })
    @Named("guardianEntityToGuardianDTO")
    public abstract GuardianDTO guardianEntityToGuardianDTO(GuardianEntity guardianEntity); 
	
    /**
     * 
     * @param guardianEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "guardianEntityToGuardianDTO")
    public abstract List<GuardianDTO> guardianEntitiesToGuardianDTOs(final List<GuardianEntity> guardianEntities);
    
    /**
     * 
     * @param registerGuardianDTO
     * @return
     */
    @Mappings({ 
		@Mapping(expression="java(passwordEncoder.encode(registerGuardianDTO.getPasswordClear()))", target = "password"),
		@Mapping(expression="java(authorityRepository.findByType(sanchez.sanchez.sergio.bullkeeper.persistence.entity.AuthorityEnum.ROLE_GUARDIAN))", target = "authority"),
        @Mapping(expression="java(com.google.i18n.phonenumbers.PhoneNumberUtil.getInstance().format(registerGuardianDTO.getTelephone(),"
        		+ " com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.E164))", target = "telephone" ),
        @Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.util.Utils.generateRandomUUID())", target = "userName" )
	})
    public abstract GuardianEntity registerGuardianDTOToGuardianEntity(RegisterGuardianDTO registerGuardianDTO);
    
    /**
     * 
     * @param registerGuardianByFacebookDTO
     * @return
     */
    @Mappings({ 
		@Mapping(expression="java(passwordEncoder.encode(registerGuardianByFacebookDTO.getPasswordClear()))", target = "password"),
		@Mapping(expression="java(authorityRepository.findByType(sanchez.sanchez.sergio.bullkeeper.persistence.entity.AuthorityEnum.ROLE_GUARDIAN))",
			target = "authority"),
        @Mapping(source="registerGuardianByFacebookDTO.telephone.rawInput", target = "telephone" ),
        @Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.util.Utils.generateRandomUUID())", target = "userName" )
	})
    public abstract GuardianEntity registerGuardianByFacebookDTOToGuardianEntity(RegisterGuardianByFacebookDTO registerGuardianByFacebookDTO);
    
    
    /**
     * 
     * @param registerGuardianByGoogleDTO
     * @return
     */
    @Mappings({ 
    	@Mapping(target = "telephone", ignore=true),
		@Mapping(expression="java(passwordEncoder.encode(registerGuardianByGoogleDTO.getPasswordClear()))", target = "password"),
		@Mapping(expression="java(authorityRepository.findByType(sanchez.sanchez.sergio.bullkeeper.persistence.entity.AuthorityEnum.ROLE_GUARDIAN))", target = "authority"),
		@Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.util.Utils.generateRandomUUID())", target = "userName" )
	})
    public abstract GuardianEntity registerGuardianByGoogleDTOToGuardianEntity(RegisterGuardianByGoogleDTO registerGuardianByGoogleDTO);
    
 
}
