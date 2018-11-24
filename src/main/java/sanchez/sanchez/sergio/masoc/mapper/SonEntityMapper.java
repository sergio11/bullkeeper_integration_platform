package sanchez.sanchez.sergio.masoc.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.masoc.domain.inject.BeansManager;
import sanchez.sanchez.sergio.masoc.domain.inject.Injectable;
import sanchez.sanchez.sergio.masoc.domain.service.IAlertService;
import sanchez.sanchez.sergio.masoc.domain.service.ITerminalService;
import sanchez.sanchez.sergio.masoc.persistence.entity.SonEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.SchoolRepository;
import sanchez.sanchez.sergio.masoc.web.dto.request.RegisterSonDTO;
import sanchez.sanchez.sergio.masoc.web.dto.request.UpdateSonDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.SonDTO;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class SonEntityMapper implements Injectable {
	
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
    
    @Mappings({
        @Mapping(expression="java(sonEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(schoolEntityMapper.schoolEntityToSchoolDTO(sonEntity.getSchool()))", target = "school" ),
        @Mapping(source = "sonEntity.birthdate", target = "birthdate", dateFormat = "yyyy/MM/dd"),
        @Mapping(source = "sonEntity.age", target = "age"),
        @Mapping(expression="java(alertservice.getTotalAlertsBySon(sonEntity.getId()))", target = "alertsStatistics" ),
        @Mapping(expression="java(terminalService.getTerminalsByChildId(sonEntity.getId().toString()))", target="terminals")
    })
    @Named("sonEntityToSonDTO")
    public abstract SonDTO sonEntityToSonDTO(SonEntity sonEntity); 
	
    /**
     * 
     * @param sonEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "sonEntityToSonDTO")
    public abstract Iterable<SonDTO> sonEntitiesToSonDTOs(Iterable<SonEntity> sonEntities);
    
    /**
     * 
     * @param registerSonDTO
     * @return
     */
    @Mappings({
        @Mapping(expression="java(schoolRepository.findOne(new org.bson.types.ObjectId(registerSonDTO.getSchool())))", target = "school" )
    })
    public abstract SonEntity registerSonDTOToSonEntity(RegisterSonDTO registerSonDTO);
    
    /**
     * 
     * @param updateSonDTO
     * @return
     */
    @Mappings({
        @Mapping(expression="java(schoolRepository.findOne(new org.bson.types.ObjectId(updateSonDTO.getSchool())))", target = "school" )
    })
    public abstract SonEntity updateSonDTOToSonEntity(UpdateSonDTO updateSonDTO);

	@Override
	public void inject(BeansManager beansManager) {
		alertservice = beansManager.getAlertService();
	}
}
