package sanchez.sanchez.sergio.admin.controller;

import javax.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RequestParam;
import sanchez.sanchez.sergio.dto.CommentDTO;
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
    
    @RequestMapping(value = "/", params = { "page", "size" }, method = GET)
    public String findPaginated(Model model, @RequestParam( "page" ) int page, 
            @RequestParam( "size" ) int size) {
        
        Page<CommentDTO> resultPage = commentsService.findPaginated( page, size );
        model.addAttribute("page", resultPage);
        return "comments";
    
    }
    
    @PostConstruct
    protected void init(){
        Assert.notNull(commentsService, "Comments Service cannot be null");
    }
    
}
