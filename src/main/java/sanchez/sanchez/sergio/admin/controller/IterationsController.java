package sanchez.sanchez.sergio.admin.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RequestParam;
import sanchez.sanchez.sergio.dto.IterationDTO;
import sanchez.sanchez.sergio.service.IIterationService;

/**
 *
 * @author sergio
 */
@Controller
@RequestMapping("/admin/iterations")
public class IterationsController {
    
    private final IIterationService iterationService;

    public IterationsController(IIterationService iterationService) {
        this.iterationService = iterationService;
    }
    
    @RequestMapping(value = "/", params = { "page", "size" }, method = GET)
    public String findPaginated(Model model, @RequestParam( "page" ) int page, 
            @RequestParam( "size" ) int size) {
        
        Page<IterationDTO> resultPage = iterationService.findPaginated( page, size );
        model.addAttribute("page", resultPage);
        return "iterations";
    
    }
    
}
