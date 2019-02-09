package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppModelEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AppModelCategoryRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppModelDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppModelDTO;

/**
 * App Model Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class AppModelEntityMapper {
	
	/**
	 * App Model Category Entity Mapper
	 */
	@Autowired
	protected AppModelCategoryEntityMapper appModelCategoryEntityMapper;
	
	/**
	 * App Model Category Repository
	 */
	@Autowired
	protected AppModelCategoryRepository appModelCategoryRepository;
	
    /**
   	 * App Model Entity to App Model DTO
   	 * @param appInstalledEntity
   	 * @return
   	 */
     @Mappings({
    	 @Mapping(expression="java(appModelCategoryEntityMapper.appModelCategoryEntityToAppModelCategoryDTO(appModelEntity.getCategory()))", 
    			 target="category")
     })
     @Named("appModelEntityToAppModelDTO")
     public abstract AppModelDTO appModelEntityToAppModelDTO(final AppModelEntity appModelEntity); 
       
       
     /**
     * 
     * @param appModelEntitiesList
     * @return
     */
     @IterableMapping(qualifiedByName = "appModelEntityToAppModelDTO")
     public abstract Iterable<AppModelDTO> appModelEntityToAppModelDTOs(
       		Iterable<AppModelEntity> appModelEntitiesList);
     
     /**
      * 
      * @param saveAppModelDTO
      * @return
      */
     @Mappings({
    	 @Mapping(expression="java(appModelCategoryRepository.findOne(saveAppModelDTO.getCatKey()))", target="category")
     })
     public abstract AppModelEntity saveAppModelDtoToAppModelEntity(final SaveAppModelDTO saveAppModelDTO);
     
     /**
      * 
      * @param saveAppModelDTOList
      * @return
      */
     @IterableMapping(qualifiedByName = "saveAppModelDtoToAppModelEntity")
     public abstract Iterable<AppModelEntity> saveAppModelDtoToAppModelEntity(final Iterable<SaveAppModelDTO> saveAppModelDTOList);
   

}
