package sanchez.sanchez.sergio.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import com.restfb.types.User;
import sanchez.sanchez.sergio.dto.request.RegisterParentByFacebookDTO;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class UserFacebookMapper {

	@Mappings({
		@Mapping(source = "userFacebook.firstName", target = "firstName"),
		@Mapping(source = "userFacebook.lastName", target = "lastName"),
		@Mapping(source = "userFacebook.birthdayAsDate", target = "birthdate"),
		@Mapping(source = "userFacebook.email", target = "email"),
		@Mapping(source = "userFacebook.id", target = "fbId"),
		@Mapping(source = "userFacebook.id", target = "passwordClear"),
		@Mapping(source = "userFacebook.id", target = "confirmPassword"),
		@Mapping(expression="java(new java.util.Locale(userFacebook.getLocale()))", target = "locale")
	})
    @Named("userFacebookToRegisterParentByFacebookDTO")
    public abstract RegisterParentByFacebookDTO userFacebookToRegisterParentByFacebookDTO(User userFacebook);
}
