package sanchez.sanchez.sergio.backend.controller;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sanchez.sanchez.sergio.service.IAlertService;

import sanchez.sanchez.sergio.service.ICommentsService;
import sanchez.sanchez.sergio.service.IIterationService;
import sanchez.sanchez.sergio.service.ISonService;

/**
 *
 * @author sergio
 */
@Controller("AdminHomeController")
@RequestMapping("/admin")
public class HomeController {
    
    private final ICommentsService commentsService;
    private final IIterationService iterationsService;
    private final ISonService sonService;
    private final IAlertService alertService;

    public HomeController(ICommentsService commentsService, 
            IIterationService iterationsService, ISonService sonService, IAlertService alertService) {
        this.commentsService = commentsService;
        this.iterationsService = iterationsService;
        this.sonService = sonService;
        this.alertService = alertService;
    }
    
    @GetMapping(value = { "", "/", "/home" })
    public String show(Model model) {
        // total comments
        model.addAttribute("totalComments", commentsService.getTotalComments());
        // total iterations
        model.addAttribute("totalIterations", iterationsService.getTotalIterations());
        // total children
        model.addAttribute("totalChildren", sonService.getTotalChildren());
        // Total Alert
        model.addAttribute("totalAlerts", alertService.getTotalAlerts());
        return "index";
    }
    
    
    @PostConstruct
    protected void init(){
        Assert.notNull(commentsService, "Comments Service cannot be null");
        Assert.notNull(iterationsService, "Iterations Service cannot be null");
        Assert.notNull(sonService, "Son Service cannot be null");
        Assert.notNull(alertService, "Alert Service cannot be null");
    }

}
