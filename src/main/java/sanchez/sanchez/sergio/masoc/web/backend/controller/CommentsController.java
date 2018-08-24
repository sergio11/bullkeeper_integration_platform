package sanchez.sanchez.sergio.masoc.web.backend.controller;

import javax.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sanchez.sanchez.sergio.masoc.domain.service.ICommentsService;
import sanchez.sanchez.sergio.masoc.web.dto.response.CommentDTO;

/**
 *
 * @author sergio
 */
@Controller
@RequestMapping("/backend/admin/comments")
public class CommentsController {
    
	private final static String VIEW_NAME = "comments";
	
    private final ICommentsService commentsService;

    public CommentsController(ICommentsService commentsService) {
        this.commentsService = commentsService;
    }
    
    @GetMapping(value = { "", "/" })
    public String findPaginated(Model model, @PageableDefault Pageable pageable) {
        Page<CommentDTO> resultPage = commentsService.findPaginated(pageable);
        model.addAttribute("page", resultPage);
        return VIEW_NAME;
    
    }
    
    @PostConstruct
    protected void init(){
        Assert.notNull(commentsService, "Comments Service cannot be null");
    }
}
