package sanchez.sanchez.sergio.admin.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sanchez.sanchez.sergio.dto.response.UserDTO;
import sanchez.sanchez.sergio.service.IUserService;

/**
 *
 * @author sergio
 */
@Controller
@RequestMapping("/admin/users")
public class UsersController {
    
    private final IUserService usersService;
   
    public UsersController(IUserService usersService) {
        this.usersService = usersService;
    }
    
    @GetMapping(value = { "", "/" })
    public String findPaginated(Model model, 
            @RequestParam( value = "page", required = false, defaultValue = "1" ) int page, 
            @RequestParam( value= "size", required = false, defaultValue = "10") int size) {
        Page<UserDTO> resultPage = usersService.findPaginated( page - 1, size );
        model.addAttribute("usersPage", resultPage);
        return "users";
    }
    
}
