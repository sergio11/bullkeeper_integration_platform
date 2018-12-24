package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.RequestTypeEntity;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveRequestDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.RequestDTO;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class RequestEntityMapper {
	
	/**
	 * Location Entity Mapper
	 */
	@Autowired
	protected LocationEntityMapper locationEntityMapper;
	
    
	/**
	 * 
	 * @param alertEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(requestEntity.getType().name())", target = "type" ),
        @Mapping(source = "requestEntity.requestAt", target = "requestAt", 
        	dateFormat = "yyyy/MM/dd HH:mm:ss"),
        @Mapping(source = "requestEntity.expiredAt", target = "expiredAt", 
    		dateFormat = "yyyy/MM/dd HH:mm:ss"),
        @Mapping(expression="java(locationEntityMapper.locationEntityToLocationDTO(requestEntity.getLocation()))",
        	target = "location")
    })
    @Named("requestTypeEntityToRequestDTO")
    public abstract RequestDTO requestTypeEntityToRequestDTO(final RequestTypeEntity requestEntity); 
	
    /**
     * 
     * @param requestEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "requestTypeEntityToRequestDTO")
    public abstract Iterable<RequestDTO> requestTypeEntityToRequestDTOs(final Iterable<RequestTypeEntity> requestEntities);
    
    /**
     * 
     * @param saveRequestDTO
     * @return
     */
    @Mappings({ 
		@Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.persistence.entity"
				+ ".RequestTypeEnum.valueOf(saveRequestDTO.getType()))", 
				target = "type"),
		@Mapping(expression="java(locationEntityMapper"
				+ ".saveLocationToLocationEntity(saveRequestDTO.getLocationDTO()))", target="location")
	})
    public abstract RequestTypeEntity saveRequestDTOToRequestEntity(SaveRequestDTO saveRequestDTO);
    
    
}
