package sanchez.sanchez.sergio.masoc.web.backend.controller;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sanchez.sanchez.sergio.masoc.domain.service.IParentsService;
import sanchez.sanchez.sergio.masoc.exception.ParentNotFoundException;
import sanchez.sanchez.sergio.masoc.web.rest.controller.BaseController;

@Controller("AccountController")
@RequestMapping("/backend/accounts")
public class AccountController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	private static String ACCOUNT_ACTIVATED_VIEW_NAME = "account_activated";
	private static String ACCOUNT_DELETED_VIEW_NAME = "account_deleted";
	private static String CANCEL_DELETE_ACCOUNT_VIEW_NAME = "cancel_account_deleted";

	private final IParentsService parentService;
	
	public AccountController( IParentsService parentService){
		this.parentService = parentService;
	}
	
	@RequestMapping(value = "/activate" , method = RequestMethod.GET)
	public String activate(@RequestParam("token") String token, Model model) {
		
		try {
			
			Boolean isActivated = parentService.activateAccount(token);
			
			if(isActivated) {
				model.addAttribute("message", messageSourceResolver.resolver("accounts.activate.success"));
			} else {
				model.addAttribute("message", messageSourceResolver.resolver("accounts.activate.failed"));
			}
			
		} catch(Exception ex) {
			logger.error(ex.toString());
			model.addAttribute("message", messageSourceResolver.resolver("accounts.generic.error"));
		}
		
		return ACCOUNT_ACTIVATED_VIEW_NAME;
	}
	
	@RequestMapping(value = "/delete" , method = RequestMethod.GET)
	public String delete(@RequestParam("token") String token, Model model) {
		
		try {
			parentService.deleteAccount(token);
			model.addAttribute("message", messageSourceResolver.resolver("accounts.delete.success"));
		} catch (ParentNotFoundException ex) {
			model.addAttribute("message", messageSourceResolver.resolver("accounts.delete.token.not.valid"));
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
			model.addAttribute("message", messageSourceResolver.resolver("accounts.generic.error"));
		}
		
		return ACCOUNT_DELETED_VIEW_NAME;
	}
	
	@RequestMapping(value = "/cancel-delete" , method = RequestMethod.GET)
	public String cancelDelete(@RequestParam("token") String token, Model model) {
		
		try {
			parentService.cancelAccountDeletionProcess(token);
			model.addAttribute("message", messageSourceResolver.resolver("accounts.delete.canceled"));
		} catch(ParentNotFoundException ex) {
			model.addAttribute("message", messageSourceResolver.resolver("accounts.generic.error"));
		} catch (Exception ex) {
			logger.error(ex.toString());
			model.addAttribute("message", messageSourceResolver.resolver("accounts.generic.error"));
		}
		
		return CANCEL_DELETE_ACCOUNT_VIEW_NAME;
	}
	
	 @RequestMapping(value = "/redirect", method = RequestMethod.GET)
	 public String redirect() throws IOException {
		 logger.debug("Redirect to ig63b03cfec6894833aaf0c2823b4a66fb://authorize");
		 return "redirect:https://www.google.es/";
	 }
}
