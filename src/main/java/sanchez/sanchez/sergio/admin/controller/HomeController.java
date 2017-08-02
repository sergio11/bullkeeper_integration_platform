package sanchez.sanchez.sergio.admin.controller;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import sanchez.sanchez.sergio.service.ICommentsService;
import sanchez.sanchez.sergio.service.IIterationService;
import sanchez.sanchez.sergio.service.IUserService;

/**
 *
 * @author sergio
 */
@Controller("AdminHomeController")
public class HomeController {
    
    private final ICommentsService commentsService;
    private final IIterationService iterationsService;
    private final IUserService userService;

    public HomeController(ICommentsService commentsService, IIterationService iterationsService, IUserService userService) {
        this.commentsService = commentsService;
        this.iterationsService = iterationsService;
        this.userService = userService;
    }
    
    @GetMapping("/admin/home")
    public String show(Model model) {
        // total comments
        model.addAttribute("totalComments", commentsService.getTotalComments());
        // total iterations
        model.addAttribute("totalIterations", iterationsService.getTotalIterations());
        // total users
        model.addAttribute("totalUsers", userService.getTotalUsers());
        return "index";
    }
    
    
    @PostConstruct
    protected void init(){
        Assert.notNull(commentsService, "Comments Service cannot be null");
        Assert.notNull(iterationsService, "Iterations Service cannot be null");
        Assert.notNull(userService, "User Service cannot be null");
    }

}
