package sanchez.sanchez.sergio.admin.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sanchez.sanchez.sergio.dto.response.IterationDTO;
import sanchez.sanchez.sergio.service.IIterationService;

/**
 *
 * @author sergio
 */
@Controller("AdminIterationsController")
@RequestMapping("/admin/iterations")
public class IterationsController {
    
    private final IIterationService iterationService;

    public IterationsController(IIterationService iterationService) {
        this.iterationService = iterationService;
    }
    
    @GetMapping(value = { "", "/" })
    public String findPaginated(Model model, 
            @RequestParam( value = "page", required = false, defaultValue = "1" ) int page, 
            @RequestParam( value= "size", required = false, defaultValue = "10") int size) {
        
        Page<IterationDTO> resultPage = iterationService.findPaginated( page - 1, size );
        model.addAttribute("page", resultPage);
        return "iterations";
    
    }
    
}
