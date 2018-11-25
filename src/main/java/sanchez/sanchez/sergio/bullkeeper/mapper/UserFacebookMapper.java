package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.restfb.types.User;

import sanchez.sanchez.sergio.bullkeeper.i18n.service.I18NService;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianByFacebookDTO;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class UserFacebookMapper {
	
	@Autowired
	protected I18NService i18nService;

	@Mappings({
		@Mapping(source = "userFacebook.firstName", target = "firstName"),
		@Mapping(source = "userFacebook.lastName", target = "lastName"),
		@Mapping(source = "userFacebook.birthdayAsDate", target = "birthdate"),
		@Mapping(source = "userFacebook.email", target = "email"),
		@Mapping(source = "userFacebook.id", target = "fbId"),
		@Mapping(source = "userFacebook.id", target = "passwordClear"),
		@Mapping(source = "userFacebook.id", target = "confirmPassword"),
		@Mapping(expression="java(i18nService.parseLocaleOrDefault(userFacebook.getLocale()))", target = "locale")
	})
    @Named("userFacebookToRegisterParentByFacebookDTO")
    public abstract RegisterGuardianByFacebookDTO userFacebookToRegisterParentByFacebookDTO(User userFacebook);
}
