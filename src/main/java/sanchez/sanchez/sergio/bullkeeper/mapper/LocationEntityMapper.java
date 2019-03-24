package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.domain.service.IGoogleMapGateway;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.LocationEntity;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterKidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveLocationDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.LocationDTO;

/**
 * Location Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class LocationEntityMapper {
	
	/**
	 * Google Map Gateway
	 */
	@Autowired
	protected IGoogleMapGateway googleMapGateway;
	
	
	/**
	 * @param locationEntity
	 * @return
	 */
    @Named("locationEntityToLocationDTO")
    public abstract LocationDTO locationEntityToLocationDTO(final LocationEntity locationEntity); 
	
    /**
     * 
     * @param locationEntityList
     * @return
     */
    @IterableMapping(qualifiedByName = "locationEntityToLocationDTO")
    public abstract Iterable<LocationDTO> locationEntityToLocationDTOs(
    		Iterable<LocationEntity> locationEntityList);
    
    /**
     * 
     * @param saveLocation
     * @return
     */
    @Mappings({})
    @Named("saveLocationToLocationEntity")
    public abstract LocationEntity saveLocationToLocationEntity(
    		final SaveLocationDTO saveLocation);
    
    
   
}
