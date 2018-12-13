package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.domain.inject.BeansManager;
import sanchez.sanchez.sergio.bullkeeper.domain.inject.Injectable;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAlertService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAuthorizationService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ITerminalService;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SchoolRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterKidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.UpdateKidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidDTO;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class KidEntityMapper implements Injectable {
	
	/**
	 * School Repository
	 */
	@Autowired
	protected SchoolRepository schoolRepository;
	
	/**
	 * School Entity Mapper
	 */
	@Autowired
	protected ISchoolEntityMapper schoolEntityMapper;
	
	/**
	 * Alert Service
	 */
	protected IAlertService alertservice;
	
	/**
	 * TErminal Service
	 */
	@Autowired
	protected ITerminalService terminalService;
	
	/**
	 * Authorization Service
	 */
	@Autowired
	protected IAuthorizationService authorizationService;
	
	/**
	 * Location Entity Mapper
	 */
	@Autowired
	protected LocationEntityMapper locationEntityMapper;
    
    @Mappings({
        @Mapping(expression="java(kidEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(schoolEntityMapper.schoolEntityToSchoolDTO(kidEntity.getSchool()))", target = "school" ),
        @Mapping(source = "kidEntity.birthdate", target = "birthdate", dateFormat = "yyyy/MM/dd"),
        @Mapping(source = "kidEntity.age", target = "age"),
        @Mapping(expression="java(alertservice.getTotalAlertsByKidAndGuardianId("
        		+ "kidEntity.getId(), authorizationService.getCurrentUserId()))", target = "alertsStatistics" ),
        @Mapping(expression="java(terminalService.getTerminalsByKidId(kidEntity.getId().toString()))", 
        	target="terminals"),
        @Mapping(expression="java(locationEntityMapper"
        		+ ".locationEntityToLocationDTO(kidEntity.getCurrentLocation()))",
        	target="currentLocation")
    })
    @Named("kidEntityToKidDTO")
    public abstract KidDTO kidEntityToKidDTO(final KidEntity kidEntity); 
	
    /**
     * 
     * @param kidEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "kidEntitiesToKidDTOs")
    public abstract Iterable<KidDTO> kidEntitiesToKidDTOs(Iterable<KidEntity> kidEntities);
    
    /**
     * 
     * @param registerKidDTO
     * @return
     */
    @Mappings({
        @Mapping(expression="java(schoolRepository.findOne(new org.bson.types.ObjectId(registerKidDTO.getSchool())))",
        		target = "school" )
    })
    public abstract KidEntity registerKidDTOToKidEntity(RegisterKidDTO registerKidDTO);
    
    /**
     * 
     * @param updateKidDTO
     * @return
     */
    @Mappings({
        @Mapping(expression="java(schoolRepository.findOne(new org.bson.types.ObjectId(updateKidDTO.getSchool())))", 
        		target = "school" )
    })
    public abstract KidEntity updateKidDTOToKidEntity(UpdateKidDTO updateKidDTO);

    /**
     * 
     */
	@Override
	public void inject(BeansManager beansManager) {
		alertservice = beansManager.getAlertService();
	}
}
