package sanchez.sanchez.sergio.bullkeeper.sse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;
import sanchez.sanchez.sergio.bullkeeper.sse.service.ISseService;


/**
 * SSE Engine
 * @author sergiosanchezsanchez
 *
 */
@Component
public class SseEngine {
	
	private static Logger logger = LoggerFactory.getLogger(SseEngine.class);

	/**
	 * Default Timeout
	 */
	private static long TIMEOUT = 30000L;

	/**
	 * Emitters
	 */
	private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
	

	
	/**
	 * Config
	 * @param emitter
	 * @param subscriberId
	 */
	private void config(final SseEmitter emitter, final String subscriberId) {
		Assert.notNull(emitter, "Emitter can not be null");
		Assert.notNull(subscriberId, "Event id can not be null");
		
		if (emitter != null) {
			emitter.onCompletion(() -> {
				logger.debug("Emitter " + emitter.toString() + " COMPLETED!");
				emitters.remove(subscriberId);
			});
			emitter.onTimeout(() -> {
				logger.debug("Emitter " + emitter.toString() + " TIMEOUT!");
				emitters.remove(subscriberId);
			});
		}
	}
	
	/**
	 * Create SSE Emitter
	 * @param subscriberId
	 * @return
	 */
	public SseEmitter createSseEmitter(final String subscriberId) {
		Assert.notNull(subscriberId, "Subscriber ID");
		
		// Create Emitter
		final SseEmitter emitter = new SseEmitter(getTimeout());
		
		// Put Emitter
		emitters.put(subscriberId, emitter);
		
		// Config Emitter
		config(emitter, subscriberId);
		
		return emitter;
		
	}
	
	/**
	 * Setup SSE emitter and publish data
	 * 
	 * @param sse
	 * @param eventData
	 */
	@Async
	public <T extends AbstractSseData> void run(final ISseService sseService, final String target, final T eventData) {
		Assert.notNull(sseService, "SSE Service can not be null");
		Assert.notNull(target, "Target can not be null");
		Assert.notNull(eventData, "Event Data can not be null");
		if(emitters.get(target) != null){
			logger.debug("Subscriber Found");
			sseService.handle(eventData);
		} 
	}
	
	
	/**
	 * @param sse
	 * @param eventDataList
	 */
	@Async
	public <T extends AbstractSseData> void run(final ISseService sseService, final String target, final Iterable<T> eventDataList) {
		Assert.notNull(sseService, "SSE Service can not be null");
		Assert.notNull(target, "Target can not be null");
		Assert.notNull(eventDataList, "Event Data List can not be null");
		if(emitters.get(target) != null){
			sseService.handle(target, eventDataList);
		} 
	}
	
	/**
	 * Get Timeout
	 * @return
	 */
	public long getTimeout() {
		return TIMEOUT;
	}

	/**
	 * Get Emitters
	 * @return
	 */
	public Map<String, SseEmitter> getEmitters() {
		return emitters;
	}

}
