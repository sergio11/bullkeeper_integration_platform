package sanchez.sanchez.sergio.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("AccountsResettingController")
@RequestMapping("/accounts/resetting")
public class ResettingController {
	
	private static Logger logger = LoggerFactory.getLogger(ResettingController.class);
	
	
	@Value("${accounts.resetting.token_ttl}")
	private Integer resettingTokenTtl;
	
	/**
	 * Solicitud de reseteo de contraseña
	 * @param model
	 * @return
	 */
	@GetMapping(value = {"", "/"})
    public String request(Model model) {
		
    }
	
	/**
	 * Envío de email para completar reseteo de contraseña
	 * @return
	 */
	@PostMapping(value = "/send-email")
	public String sendEmail(){
		
	}
	
	
	/**
	 * Envío de email para completar reseteo de contraseña
	 * @return
	 */
	@GetMapping(value = "/check-email")
	public String checkEmail(){
		
	}
	
	
	@GetMapping(value = "/reset")
	public String reset(){
		
	}
	
	@PostMapping(value = "/reset/{token}")
	public String reset(@PathVariable String token){
		
		
		
	}
	
	

}
