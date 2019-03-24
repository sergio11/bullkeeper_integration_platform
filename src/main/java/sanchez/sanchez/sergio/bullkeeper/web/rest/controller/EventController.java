package sanchez.sanchez.sergio.bullkeeper.web.rest.controller;


import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import sanchez.sanchez.sergio.bullkeeper.events.sse.SubscriberSseEmitterCreated;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.sse.SseEngine;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@RestController("RestEventController")
@Validated
@RequestMapping("/api/v1/events/")
@Api(tags = "events", value = "/events/", 
	description = "Event subscription API", produces = "application/json")
public class EventController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(EventController.class);

	/**
	 * SSE Engine
	 */
	private final SseEngine sseEngine;
	
	/**
	 * Application Event Publisher
	 */
	private final ApplicationEventPublisher applicationEventPublisher;
	
	/**
	 * 
	 * @param sseEngine
	 * @param applicationEventPublisher
	 */
	@Autowired
	public EventController(final SseEngine sseEngine, final ApplicationEventPublisher applicationEventPublisher){
		this.sseEngine = sseEngine;
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	
	
	/**
	 * Subscribe To Events
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/subscribe/{id}", 
			method = RequestMethod.GET, produces = "text/event-stream;charset=UTF-8")
    //@OnlyAccessForGuardian
    @ApiOperation(value = "SUBSCRIBE_TO_EVENTS", 
    	nickname = "SUBSCRIBE_TO_EVENTS", notes = "Subscribe to events")
    public SseEmitter subscribeToEvents(
    		@ApiParam(name= "id", value = "Subscriber identifier", 
    				required = true)
				@Valid @ValidObjectId(message = "{id.not.valid}")
		 		@PathVariable String id) {
		
		logger.debug("Subscriber with id -> " + id);
		
		// Create SSE Emitter
		final SseEmitter sseEmitter = sseEngine.createSseEmitter(id);
		
		// Publish Event
		applicationEventPublisher.publishEvent(
				new SubscriberSseEmitterCreated(this, id));
		
		return sseEmitter;
    }
	
	
	// handle normal "Async timeout", to avoid logging warn messages every 30s per client...
	@ExceptionHandler(value = AsyncRequestTimeoutException.class)  
	public String asyncTimeout(AsyncRequestTimeoutException e){
		logger.debug("Async Request Timeout");
	    return null; // "SSE timeout..OK";  
	}

}
