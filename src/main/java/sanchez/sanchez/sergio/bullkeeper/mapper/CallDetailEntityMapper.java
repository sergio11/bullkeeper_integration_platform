package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CallDetailEntity;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CallDetailDTO;

/**
 * Call Detail Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class CallDetailEntityMapper {
	
	
	/**
	 * @param callDetailEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(callDetailEntity.getId().toString())", 
        		target = "identity" ),
        @Mapping(expression="java(callDetailEntity.getCallType().name())", target = "callType" ),
        @Mapping(expression="java(callDetailEntity.getTerminal().getId().toString())", 
        		target = "terminal" ),
        @Mapping(expression="java(callDetailEntity.getKid().getId().toString())", 
			target = "kid" ),
        @Mapping(source = "callDetailEntity.callDayTime", target = "callDayTime", dateFormat = "yyyy/MM/dd"),
    })
    @Named("callDetailEntityToCallDetailDTO")
    public abstract CallDetailDTO callDetailEntityToCallDetailDTO(final CallDetailEntity callDetailEntity); 
	
    /**
     * 
     * @param callDetailEntityList
     * @return
     */
    @IterableMapping(qualifiedByName = "callDetailEntityToCallDetailDTO")
    public abstract Iterable<CallDetailDTO> callDetailEntityToCallDetailDTOs(
    		Iterable<CallDetailEntity> callDetailEntityList);
    
   
}
