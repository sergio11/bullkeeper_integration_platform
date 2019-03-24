package sanchez.sanchez.sergio.bullkeeper.mapper;

import java.util.Calendar;
import java.util.Date;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidRequestEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddKidRequestDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidRequestDTO;

/**
 * Kid Request Entity Mapper
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class KidRequestEntityMapper {
	
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
	 * Kid Entity Mapper
	 */
	@Autowired
	protected KidEntityMapper kidEntityMapper;
	
	/**
	 * Terminal Entity Data Mapper
	 */
	@Autowired
	protected TerminalEntityDataMapper terminalEntityMapper;
	
	/**
	 * Location Entity Mapper
	 */
	@Autowired
	protected LocationEntityMapper locationEntityMapper;
	
	/**
	 * Pretty Time
	 */
	@Autowired
	protected PrettyTime prettyTime;
	

    
	/**
	 * 
	 * @param kidRequestEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(kidRequestEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(kidRequestEntity.getType().name())", target = "type" ),
        @Mapping(expression="java(kidEntityMapper.kidEntityToKidDTO(kidRequestEntity.getKid()))", target = "kid" ),
        @Mapping(expression="java(terminalEntityMapper.terminalEntityToTerminalDTO(kidRequestEntity.getTerminal()))", target = "terminal" ),
        @Mapping(expression="java(kidRequestEntity.getLocation() != null ? locationEntityMapper.locationEntityToLocationDTO(kidRequestEntity.getLocation()) : null )", target = "location" ),
        @Mapping(source = "kidRequestEntity.requestAt", target = "requestAt", dateFormat = "yyyy/MM/dd HH:mm:ss"),
        @Mapping(source = "kidRequestEntity.expiredAt", target = "expiredAt", dateFormat = "yyyy/MM/dd HH:mm:ss"),
        @Mapping(expression="java(prettyTime.format(kidRequestEntity.getRequestAt()))", target = "since")
    })
    @Named("kidRequestEntityToKidRequestDTO")
    public abstract KidRequestDTO kidRequestEntityToKidRequestDTO(final KidRequestEntity kidRequestEntity); 
	
    /**
     * 
     * @param kidRequestEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "kidRequestEntityToKidRequestDTO")
    public abstract Iterable<KidRequestDTO> kidRequestEntityToKidRequestDTOs(Iterable<KidRequestEntity> kidRequestEntities);
    
    
	/**
	 * Get Expired At For Request
	 * @return
	 */
	protected Date getExpiredAtForRequest() {
		final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
		Calendar date = Calendar.getInstance();
		long t= date.getTimeInMillis();
		return new Date(t + (10 * ONE_MINUTE_IN_MILLIS));
	}
    
    /**
     * 
     * @param addKidRequestDTO
     * @return
     */
   @Mappings({ 
		@Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.persistence.entity"
				+ ".RequestTypeEnum.valueOf(addKidRequestDTO.getType()))", 
				target = "type"),
		@Mapping(expression="java(addKidRequestDTO.getLocation() != null ? locationEntityMapper"
				+ ".saveLocationToLocationEntity(addKidRequestDTO.getLocation()): null)", target="location"),
		@Mapping(expression="java(kidRepository.findOne(new org.bson.types.ObjectId(addKidRequestDTO.getKid())))", 
				target = "kid"),
		@Mapping(expression="java(terminalRepository.findOne(new org.bson.types.ObjectId(addKidRequestDTO.getTerminal())))", 
				target = "terminal"),
		@Mapping(expression="java(getExpiredAtForRequest())", 
			target = "expiredAt")
		
	})
    public abstract KidRequestEntity addKidRequestDTOToKidRequestEntity(final AddKidRequestDTO addKidRequestDTO);
   
    
}
