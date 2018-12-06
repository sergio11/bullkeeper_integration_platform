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
	 * @param eventId
	 */
	private void config(final SseEmitter emitter, final String eventId) {
		Assert.notNull(emitter, "Emitter can not be null");
		Assert.notNull(eventId, "Event id can not be null");
		
		if (emitter != null) {
			emitter.onCompletion(() -> {
				logger.debug("Emitter " + emitter.toString() + " COMPLETED!");
				emitters.remove(eventId);
			});
			emitter.onTimeout(() -> {
				logger.debug("Emitter " + emitter.toString() + " TIMEOUT!");
				emitters.remove(eventId);
			});
		}
	}
	
	/**
	 * Setup SSE emitter and publish data
	 * 
	 * @param sse
	 * @param eventData
	 */
	@Async
	public <T extends AbstractSseData> void run(final ISseService<T> sseService, final T eventData) {
		if(emitters.get(eventData.getSubscriberId()) != null){
			config(emitters.get(eventData.getSubscriberId()), eventData.getSubscriberId());
			sseService.handle(eventData);
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
