package sanchez.sanchez.sergio.admin.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RequestParam;
import sanchez.sanchez.sergio.dto.UserDTO;
import sanchez.sanchez.sergio.mapper.IUserEntityMapper;
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
    
    @RequestMapping(value = "/", params = { "page", "size" }, method = GET)
    public String findPaginated(Model model, @RequestParam( "page" ) int page, 
            @RequestParam( "size" ) int size) {
        
        Page<UserDTO> resultPage = usersService.findPaginated( page, size );
        model.addAttribute("page", resultPage);
        return "users";
    
    }
    
}
