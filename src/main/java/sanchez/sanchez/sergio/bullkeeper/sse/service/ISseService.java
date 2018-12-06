package sanchez.sanchez.sergio.bullkeeper.sse.service;

import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * SSE Service
 * @author sergiosanchezsanchez
 *
 */
public interface ISseService<T extends AbstractSseData> {
	
	/**
	 * Push data to SSE channel associated to an event ID
	 * 
	 * @param subscriberId
	 * @param data
	 */
	public void push(String subscriberId, T data);

	/**
	 * This method does the necessary actions to publish the event with the proper data
	 * @param eventData
	 */
	public void handle(T eventData);

}
