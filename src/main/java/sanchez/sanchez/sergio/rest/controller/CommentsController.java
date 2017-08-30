package sanchez.sanchez.sergio.rest.controller;


import java.util.Optional;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import sanchez.sanchez.sergio.dto.response.CommentDTO;
import sanchez.sanchez.sergio.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.CommentNotFoundException;
import sanchez.sanchez.sergio.rest.exception.NoCommentsFoundException;
import sanchez.sanchez.sergio.rest.hal.ICommentHAL;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.CommentResponseCode;
import sanchez.sanchez.sergio.security.utils.OnlyAccessForAdmin;
import sanchez.sanchez.sergio.service.ICommentsService;
import springfox.documentation.annotations.ApiIgnore;


@RestController("RestCommentsController")
@Validated
@RequestMapping("/api/v1/comments/")
@Api(tags = "comments", value = "/comments/", description = "Punto de Entrada para el manejo de comentarios y/o opiniones", produces = "application/json")
public class CommentsController extends BaseController implements ICommentHAL {

    private static Logger logger = LoggerFactory.getLogger(CommentsController.class);
    
    private final ICommentsService commentsService;

    public CommentsController(ICommentsService commentsService) {
        this.commentsService = commentsService;
    }
    
    @RequestMapping(value = {"/", "/all"}, method = RequestMethod.GET)
    @OnlyAccessForAdmin
    @ApiOperation(value = "GET_ALL_COMMENTS", nickname = "GET_ALL_COMMENTS", 
            notes = "Get all Comments", response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<CommentDTO>>>> getAllComments(
    		@ApiIgnore @PageableDefault Pageable pageable, 
    		@ApiIgnore PagedResourcesAssembler<CommentDTO> pagedAssembler) throws Throwable {
        logger.debug("Get all Comments");
        
        Page<CommentDTO> commentsPage = commentsService.findPaginated(pageable);
        
        if(commentsPage.getTotalElements() == 0)
        	throw new NoCommentsFoundException();
        
        return ApiHelper.<PagedResources<Resource<CommentDTO>>>createAndSendResponse(CommentResponseCode.ALL_COMMENTS, 
        		HttpStatus.OK, pagedAssembler.toResource(addLinksToComments((commentsPage))));
       
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @OnlyAccessForAdmin
    @ApiOperation(value = "GET_COMMENT_BY_ID", nickname = "GET_COMMENT_BY_ID", notes = "Get Comment By Id",
            response = CommentDTO.class)
    public ResponseEntity<APIResponse<CommentDTO>> getCommentById(
    		@ApiParam(name= "id", value = "Identificador del comentario", required = true)
    			@Valid @ValidObjectId(message = "{comment.id.notvalid}")
    		 		@PathVariable String id) throws Throwable {
        logger.debug("Get Comment with id: " + id);
        return Optional.ofNullable(commentsService.getCommentById(id))
                .map(commentResource -> addLinksToComment(commentResource))
                .map(commentResource -> ApiHelper.<CommentDTO>createAndSendResponse(CommentResponseCode.SINGLE_COMMENT, 
                		HttpStatus.OK, commentResource))
                .orElseThrow(() -> { throw new CommentNotFoundException(); });
    }
}
