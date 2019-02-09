package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppModelCategoryEntity;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppModelCategoryDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppModelCategoryDTO;

/**
 * App Model Category Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class AppModelCategoryEntityMapper {
	
    /**
   	 * App Model Category Entity to App Model Category DTO
   	 * @param appInstalledEntity
   	 * @return
   	 */
     @Mappings({
    	 @Mapping(expression="java(appModelCategoryEntity.getDefaultRule().name())", 
    			 target="defaultRule")
     })
     @Named("appModelCategoryEntityToAppModelCategoryDTO")
     public abstract AppModelCategoryDTO appModelCategoryEntityToAppModelCategoryDTO(
    		 final AppModelCategoryEntity appModelCategoryEntity); 
       
       
     /**
     * @param appModelCategoryEntitiesList
     * @return
     */
     @IterableMapping(qualifiedByName = "appModelCategoryEntityToAppModelCategoryDTO")
     public abstract Iterable<AppModelCategoryDTO> appModelCategoryEntityToAppModelCategoryDTOs(
       		Iterable<AppModelCategoryEntity> appModelCategoryEntitiesList);
     
     /**
      * 
      * @param saveAppModelCategoryDTO
      * @return
      */
     @Mappings({
    	 @Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppRuleEnum.valueOf(saveAppModelCategoryDTO.getDefaultRule()))", 
    			 	target="defaultRule")
     })
     public abstract AppModelCategoryEntity saveAppModelCategoryDtoToAppModelCategoryEntity(final SaveAppModelCategoryDTO saveAppModelCategoryDTO);
     
     /**
      * 
      * @param saveAppModelDTOList
      * @return
      */
     @IterableMapping(qualifiedByName = "saveAppModelCategoryDtoToAppModelCategoryEntity")
     public abstract Iterable<AppModelCategoryEntity> saveAppModelCategoryDtoToAppModelCategoryEntity(final Iterable<SaveAppModelCategoryDTO> saveAppModelCategoryDTOList);
   

}
