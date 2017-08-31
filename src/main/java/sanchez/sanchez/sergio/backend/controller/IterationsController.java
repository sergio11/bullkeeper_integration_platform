package sanchez.sanchez.sergio.backend.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sanchez.sanchez.sergio.dto.response.CommentsBySonDTO;
import sanchez.sanchez.sergio.dto.response.IterationDTO;
import sanchez.sanchez.sergio.service.IIterationService;
import sanchez.sanchez.sergio.websocket.constants.WebSocketConstants;

/**
 *
 * @author sergio
 */
@Controller("AdminIterationsController")
@RequestMapping("/admin/iterations")
public class IterationsController {
    
    private static Logger logger = LoggerFactory.getLogger(IterationsController.class);

    private final static String VIEW_NAME = "iterations";

    private final IIterationService iterationService;

    public IterationsController(IIterationService iterationService) {
        this.iterationService = iterationService;
    }

    @GetMapping(value = {"", "/"})
    public String findPaginated(Model model, @PageableDefault Pageable pageable) {
        Page<IterationDTO> resultPage = iterationService.findPaginated(pageable);
        model.addAttribute("page", resultPage);
        return VIEW_NAME;
    }
    
    @MessageMapping("/web-socket/admin/iterations")
    @SendTo(WebSocketConstants.ALL_ITERATIONS_TOPIC)
    public List<IterationDTO> allIterations() throws Exception {
        logger.debug("Web Socket Entry Point called ...");
        return iterationService.allIterations();
    }
    
    @MessageMapping("/web-socket/admin/iterations/last/comments-by-son")
    @SendTo(WebSocketConstants.LAST_ITERATION_COMMENTS_BY_SON_TOPIC)
    public List<CommentsBySonDTO> commentsBySonForLastIteration() throws Exception {
        return iterationService.getCommentsBySonForLastIteration();
    }
    
}
