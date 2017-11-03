package es.bisite.usal.bulltect.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import es.bisite.usal.bulltect.persistence.entity.PreferencesEntity;
import es.bisite.usal.bulltect.web.dto.response.UserSystemPreferencesDTO;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class PreferencesEntityMapper {
	
	@Mappings({
		@Mapping(expression="java(preferencesEntity.getRemoveAlertsEvery().name())", target = "removeAlertsEvery" )
	})
    @Named("preferencesEntityToUserSystemPreferencesDTO")
    public abstract UserSystemPreferencesDTO preferencesEntityToUserSystemPreferencesDTO(PreferencesEntity preferencesEntity); 
	
    @IterableMapping(qualifiedByName = "preferencesEntityToUserSystemPreferencesDTO")
    public abstract Iterable<UserSystemPreferencesDTO> preferencesEntitiesToUserSystemPreferencesDTO(Iterable<PreferencesEntity> preferencesEntities);

    
}
