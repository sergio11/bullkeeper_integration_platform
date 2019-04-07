package sanchez.sanchez.sergio.bullkeeper.events.photos.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.events.photos.DevicePhotoDisabledEvent;
import sanchez.sanchez.sergio.bullkeeper.sse.models.photos.DevicePhotoDisabledSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.service.ISseService;

/**
 * Device Photos Event Handlers
 * @author ssanchez
 *
 */
@Component
public class DevicePhotosEventHandlers {
	
	private static Logger logger = LoggerFactory.getLogger(DevicePhotosEventHandlers.class);
	
	/**
	 * SSE Service
	 */
	private final ISseService sseService;


	/**
	 * 
	 * @param sseService
	 * @param terminalRepository
	 */
	public DevicePhotosEventHandlers(final ISseService sseService) {
		super();
		this.sseService = sseService;
	}
	
	
	/**
	 * Handle Device Photo Disabled Event
	 * @param devicePhotoDisabledEvent
	 */
	@EventListener
	public void handle(final DevicePhotoDisabledEvent devicePhotoDisabledEvent) {
		Assert.notNull(devicePhotoDisabledEvent, "Device Photo Disabled event can not be null");
		
		final String subscriberId = devicePhotoDisabledEvent.getTerminal();
		
		sseService.push(subscriberId, new DevicePhotoDisabledSSE(
				subscriberId,
				devicePhotoDisabledEvent.getKid(),
				devicePhotoDisabledEvent.getTerminal(),
				devicePhotoDisabledEvent.getPhoto(),
				devicePhotoDisabledEvent.getLocalId(),
				devicePhotoDisabledEvent.getPath()));
	}

}
