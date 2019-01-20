package sanchez.sanchez.sergio.bullkeeper.sse.service;

import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * SSE Service
 * @author sergiosanchezsanchez
 *
 */
public interface ISseService {
	
	/**
	 * Push data to SSE channel associated to an event ID
	 * 
	 * @param subscriberId
	 * @param data
	 */
	public <T extends AbstractSseData>  void push(final String subscriberId, final T data);
	
	/**
	 * Push data to SSE channel associated to an event ID
	 * 
	 * @param subscriberId
	 * @param data
	 */
	public <T extends AbstractSseData>  void push(final String subscriberId, final Iterable<T> data);
	
	/**
	 * Push data to SSE channel associated to an event ID
	 * 
	 * @param subscriberIdList
	 * @param data
	 */
	public <T extends AbstractSseData>  void push(final Iterable<String> subscriberIdList, final T data);
	
	/**
	 * Push data to SSE channel associated to an event ID
	 * 
	 * @param subscriberIdList
	 * @param data
	 */
	public <T extends AbstractSseData>  void push(final Iterable<String> subscriberIdList, final Iterable<T> data);

	/**
	 * This method does the necessary actions to publish the event with the proper data
	 * @param eventData
	 */
	public <T extends AbstractSseData>  void handle(final T eventData);
	
	/**
	 * This method does the necessary actions to publish the event with the proper data
	 * @param subscriberId
	 * @param eventDataList
	 */
	public <T extends AbstractSseData>  void handle(final String subscriberId, final Iterable<T> eventDataList);

	
	/**
	 * @param eventData
	 */
	public <T extends AbstractSseData>  void save(final T eventData);
	
	/**
	 * @param subscriberId
	 * @param eventDataList
	 */
	public <T extends AbstractSseData>  void save(final String subscriberId, final Iterable<T> eventDataList);
	
	
	
	
}
