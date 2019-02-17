package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SupervisedChildrenEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.MessageRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SupervisedChildrenDTO;

/**
 * Supervised Children Entity
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class SupervisedChildrenEntityMapper {
	
	/**
	 * Kid Entity Mapper
	 */
	@Autowired
	protected KidEntityMapper kidEntityMapper;
	
	/**
	 * Guardian Entity Mapper
	 */
	@Autowired
	protected GuardianEntityMapper guardianEntityMapper;
	
	/**
	 * Kid Repository
	 */
	@Autowired
	protected KidRepository kidRepository;
	
	/**
	 * Guardian Repository
	 */
	@Autowired
	protected GuardianRepository guardianRepository;
	
	/**
	 * Supervised Children Repository
	 */
	@Autowired
	protected SupervisedChildrenRepository supervisedChildrenRepository;
	
	/**
	 * Message Repository
	 */
	@Autowired
	protected MessageRepository messageRepository;

	
	/**
	 * 
	 * @param supervisedChildrenEntity
	 * @return
	 */
	@Mappings({
        @Mapping(expression="java(supervisedChildrenEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(kidEntityMapper.kidEntityToKidDTO(supervisedChildrenEntity.getKid()))", target = "kid" ),
        @Mapping(expression="java(supervisedChildrenEntity.getRole().name())", target = "role" ),
        @Mapping(expression="java(messageRepository.countByFromIdAndToIdAndViewedFalse(supervisedChildrenEntity.getKid().getId(), supervisedChildrenEntity.getGuardian().getId()))", 
    		target = "pendingMessagesCount" )
    })
    @Named("supervisedChildrenEntityToSupervisedChildrenDTO")
    public abstract SupervisedChildrenDTO supervisedChildrenEntityToSupervisedChildrenDTO(
    		final SupervisedChildrenEntity supervisedChildrenEntity); 
	
    /**
     * 
     * @param kidEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "supervisedChildrenEntityToSupervisedChildrenDTO")
    public abstract Iterable<SupervisedChildrenDTO> supervisedChildrenEntitiesToSupervisedChildrenDTOs(final Iterable<SupervisedChildrenEntity> supervisedChildrenEntities);
    
    
    
    /**
	 * Save Guardian DTO to Supervised Children Entity
	 * @param saveGuardianDTO
	 * @return
	 */
	@Mappings({
		@Mapping(expression="java(saveGuardianDTO.getIdentity() != null "
				+ "&& !saveGuardianDTO.getIdentity().isEmpty() ? new org.bson.types.ObjectId(saveGuardianDTO.getIdentity()) : null)",
				 target = "id" ),
		 @Mapping(expression="java(kidRepository.findOne(new org.bson.types.ObjectId(saveGuardianDTO.getKid())))",
				 target = "kid" ),
		 @Mapping(expression="java(guardianRepository.findOne(new org.bson.types.ObjectId(saveGuardianDTO.getGuardian())))",
 				target = "guardian"),
		 @Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianRolesEnum.valueOf(saveGuardianDTO.getRole()))", 
		 		target = "role")
    })
    @Named("saveGuardianDTOToSupervisedChildrenEntity")
    public abstract SupervisedChildrenEntity saveGuardianDTOToSupervisedChildrenEntity(
    		final SaveGuardianDTO saveGuardianDTO); 
	
    /**
     * Save Guardian DTO to Supervised Children Entity
     * @param saveGuardianDTOs
     * @return
     */
    @IterableMapping(qualifiedByName = "saveGuardianDTOToSupervisedChildrenEntity")
    public abstract Iterable<SupervisedChildrenEntity> saveGuardianDTOToSupervisedChildrenEntity(
    		final Iterable<SaveGuardianDTO> saveGuardianDTOs);
    
    
    /**
	 * 
	 * @param supervisedChildrenEntity
	 * @return
	 */
	@Mappings({
        @Mapping(expression="java(supervisedChildrenEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(kidEntityMapper.kidEntityToKidDTO(supervisedChildrenEntity.getKid()))", 
        	target = "kid" ),
        @Mapping(expression="java(guardianEntityMapper.guardianEntityToGuardianDTO(supervisedChildrenEntity.getGuardian()))", 
        	target = "guardian" ),
        @Mapping(source = "supervisedChildrenEntity.requestAt", target = "requestAt", dateFormat = "yyyy/MM/dd"),
        @Mapping(expression="java(supervisedChildrenEntity.getRole().name())", 
        	target = "role" ),
        @Mapping(expression="java(messageRepository.countByFromIdAndToIdAndViewedFalse(supervisedChildrenEntity.getKid().getId(), supervisedChildrenEntity.getGuardian().getId()))", 
    		target = "pendingMessagesCount" )
    })
    @Named("supervisedChildrenEntityToKidGuardiansDTO")
    public abstract KidGuardianDTO supervisedChildrenEntityToKidGuardiansDTO(
    		final SupervisedChildrenEntity supervisedChildrenEntity); 
	
    /**
     * 
     * @param kidEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "supervisedChildrenEntityToKidGuardiansDTO")
    public abstract Iterable<KidGuardianDTO> supervisedChildrenEntityToKidGuardiansDTO(final Iterable<SupervisedChildrenEntity> supervisedChildrenEntities);
    

}
