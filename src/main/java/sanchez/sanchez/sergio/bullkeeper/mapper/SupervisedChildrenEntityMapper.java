package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SupervisedChildrenEntity;
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
	 * 
	 * @param supervisedChildrenEntity
	 * @return
	 */
	@Mappings({
        @Mapping(expression="java(supervisedChildrenEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(kidEntityMapper.kidEntityToKidDTO(supervisedChildrenEntity.getKid()))", target = "kid" ),
        @Mapping(source = "supervisedChildrenEntity.requestAt", target = "requestAt", dateFormat = "yyyy/MM/dd"),
        @Mapping(expression="java(supervisedChildrenEntity.getRole().name())", target = "role" )
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

}
