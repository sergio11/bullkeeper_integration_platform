package sanchez.sanchez.sergio.admin.controller;

import javax.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sanchez.sanchez.sergio.dto.response.CommentDTO;
import sanchez.sanchez.sergio.service.ICommentsService;

/**
 *
 * @author sergio
 */
@Controller
@RequestMapping("/admin/comments")
public class CommentsController {
    
    private final ICommentsService commentsService;

    public CommentsController(ICommentsService commentsService) {
        this.commentsService = commentsService;
    }
    
    @GetMapping(value = { "", "/" })
    public String findPaginated(Model model, 
            @RequestParam( value = "page", required = false, defaultValue = "1" ) int page, 
            @RequestParam( value= "size", required = false, defaultValue = "10") int size) {
        
        Page<CommentDTO> resultPage = commentsService.findPaginated( page - 1, size );
        model.addAttribute("page", resultPage);
        return "comments";
    
    }
    
    @PostConstruct
    protected void init(){
        Assert.notNull(commentsService, "Comments Service cannot be null");
    }
    
}
