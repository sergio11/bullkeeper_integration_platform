package es.bisite.usal.bulltect.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.google.api.services.oauth2.model.Userinfoplus;
import es.bisite.usal.bulltect.web.dto.request.RegisterParentByGoogleDTO;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class GoogleMapper {
	
	@Mappings({
		@Mapping(source = "userInfo.givenName", target = "firstName"),
		@Mapping(source = "userInfo.familyName", target = "lastName"),
		@Mapping(source = "userInfo.email", target = "email"),
		@Mapping(source = "userInfo.id", target = "googleId"),
		@Mapping(source = "userInfo.id", target = "passwordClear"),
		@Mapping(source = "userInfo.id", target = "confirmPassword"),
		@Mapping(expression="java(new java.util.Locale(userInfo.getLocale() != null ? userInfo.getLocale().replace('-', '_'): \"en\"))", target = "locale")
	})
    @Named("userInfoPlusToRegisterParentByGoogleDTO")
	public abstract RegisterParentByGoogleDTO userInfoPlusToRegisterParentByGoogleDTO(Userinfoplus userInfo); 
	
    @IterableMapping(qualifiedByName = "userInfoPlusToRegisterParentByGoogleDTO")
    public abstract List<RegisterParentByGoogleDTO> usersInfoPlusToRegisterParentByGoogleDTO(List<Userinfoplus> usersInfo);

}
