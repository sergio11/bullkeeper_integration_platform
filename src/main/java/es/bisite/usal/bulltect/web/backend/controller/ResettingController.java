package es.bisite.usal.bulltect.web.backend.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.bisite.usal.bulltect.domain.service.IAuthorizationService;
import es.bisite.usal.bulltect.domain.service.IParentsService;
import es.bisite.usal.bulltect.domain.service.IPasswordResetTokenService;
import es.bisite.usal.bulltect.events.PasswordChangedEvent;
import es.bisite.usal.bulltect.persistence.entity.ParentEntity;
import es.bisite.usal.bulltect.web.dto.request.UpdatePasswordDTO;
import es.bisite.usal.bulltect.web.rest.controller.BaseController;
import es.bisite.usal.bulltect.web.security.utils.OnlyAccessWithChangePasswordPrivilege;

@Controller("AccountsResettingController")
@RequestMapping("/backend/accounts/resetting")
@SessionAttributes({ResettingController.ATTRIBUTE_NAME})
public class ResettingController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(ResettingController.class);
	
	private final static String CHANGE_PASSWORD_VIEW_NAME = "change_password";
	
	public static final String ATTRIBUTE_NAME = "updatePassword";
    public static final String BINDING_RESULT_NAME = "org.springframework.validation.BindingResult." + ATTRIBUTE_NAME;

	private final IPasswordResetTokenService passwordResetTokenService;
	private final IAuthorizationService authorizationService;
	private final IParentsService parentService;
	
	public ResettingController(IPasswordResetTokenService passwordResetTokenService, IAuthorizationService authorizationService, 
			IParentsService parentService){
		this.passwordResetTokenService = passwordResetTokenService;
		this.authorizationService = authorizationService;
		this.parentService = parentService;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String show(@RequestParam("id") String id, 
			@RequestParam("token") String token, Model model) {
		
		if(!passwordResetTokenService.isValid(id, token)){
			model.addAttribute("message", 
					messageSourceResolver.resolver("resetting.token.invalid", new Object[] { token }));
			return "redirect:/backend/accounts/resetting/invalid-token";
		}
	
		if(!model.containsAttribute(BINDING_RESULT_NAME)) {
            model.addAttribute(ATTRIBUTE_NAME, new UpdatePasswordDTO());
        }
		
		authorizationService.grantChangePasswordPrivilege(id);
		
		return CHANGE_PASSWORD_VIEW_NAME;
	}
	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@OnlyAccessWithChangePasswordPrivilege
	public String changePassword(
			@Valid @ModelAttribute(ATTRIBUTE_NAME) UpdatePasswordDTO updatePassword,
			BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            // SessionStatus lets you clear your SessionAttributes
            SessionStatus sessionStatus) {
		
		if(bindingResult.hasErrors()) {
            //put the validation errors in Flash session and redirect to self
            redirectAttributes.addFlashAttribute(BINDING_RESULT_NAME, bindingResult);
            return CHANGE_PASSWORD_VIEW_NAME;
        }
		
		ParentEntity parent = 
	      (ParentEntity) SecurityContextHolder.getContext()
	                                  .getAuthentication().getPrincipal();
	    
		logger.debug("Parent id: " + parent.getId() + " Confirm Password " +  updatePassword.getConfirmPassword());
		parentService.changeUserPassword(parent.getId(), updatePassword.getConfirmPassword());
		applicationEventPublisher.publishEvent(new PasswordChangedEvent(this, parent.getId().toString()));
		sessionStatus.setComplete();
		 SecurityContextHolder.clearContext();
		return "redirect:/backend/accounts/resetting/password-changed";
	}
	
}
