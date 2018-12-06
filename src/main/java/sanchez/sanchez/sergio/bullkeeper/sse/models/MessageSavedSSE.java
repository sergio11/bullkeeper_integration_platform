package sanchez.sanchez.sergio.bullkeeper.sse.models;

import java.io.Serializable;

/**
 * Message Saved SSE
 * @author sergiosanchezsanchez
 *
 */
public class MessageSavedSSE extends AbstractSseData implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Message Saved SSE
	 * @param subscriberId
	 */
	public MessageSavedSSE(String subscriberId) {
		super(subscriberId);
		// TODO Auto-generated constructor stub
	}

}
