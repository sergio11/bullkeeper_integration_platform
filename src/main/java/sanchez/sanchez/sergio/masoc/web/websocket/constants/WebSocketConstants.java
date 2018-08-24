package sanchez.sanchez.sergio.masoc.web.websocket.constants;

import java.io.Serializable;

public class WebSocketConstants implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public final static String NEW_ITERATION_TOPIC = "/topic/iterations/new";
	public final static String LAST_ITERATION_COMMENTS_BY_SON_TOPIC = "/topic/iterations/last/comments-by-son";
	public final static String ALL_ITERATIONS_TOPIC = "/topic/iterations/all";

}
