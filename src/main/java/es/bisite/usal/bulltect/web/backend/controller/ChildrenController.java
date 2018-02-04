package es.bisite.usal.bulltect.web.backend.controller;

import javax.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.bisite.usal.bulltect.domain.service.ISonService;
import es.bisite.usal.bulltect.web.dto.response.SonDTO;

/**
 *
 * @author sergio
 */
@Controller("AdminChildrenController")
@RequestMapping("/backend/admin/children")
public class ChildrenController {
	
	private final static String VIEW_NAME = "children";
    
    private final ISonService sonService;

    public ChildrenController(ISonService sonService) {
        this.sonService = sonService;
    }
    
    @GetMapping(value = { "", "/" })
    public String findPaginated(Model model, @PageableDefault Pageable pageable) {
        Page<SonDTO> resultPage = sonService.findPaginated(pageable);
        model.addAttribute("page", resultPage);
        return VIEW_NAME;
    }
    
    @PostConstruct
    protected void init(){
        Assert.notNull(sonService, "Son Service cannot be null");
    }
    
}
