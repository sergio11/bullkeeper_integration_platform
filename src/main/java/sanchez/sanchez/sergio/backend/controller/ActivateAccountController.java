package sanchez.sanchez.sergio.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sanchez.sanchez.sergio.rest.controller.BaseController;
import sanchez.sanchez.sergio.service.IParentsService;

@Controller("ActivateAccountController")
@RequestMapping("/backend/accounts/activate")
public class ActivateAccountController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(ActivateAccountController.class);

	private final IParentsService parentService;
	
	public ActivateAccountController( IParentsService parentService){
		this.parentService = parentService;
	}
	
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String show(@RequestParam("token") String token, 
			RedirectAttributes model) {
		
		Boolean isActivated = parentService.activateAccount(token);
		
		if(!isActivated)
			return "redirect:/backend/accounts/activate/invalid";
			
		//applicationEventPublisher.publishEvent(new ParentAccountActivatedEvent("", this));
		return "redirect:/backend/accounts/activate/active";
	}
	
}
