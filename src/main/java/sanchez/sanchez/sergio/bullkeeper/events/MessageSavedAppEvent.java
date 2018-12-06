package sanchez.sanchez.sergio.bullkeeper.events;


import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseApplicationEvent;

/**
 * Message Saved App Event
 * @author sergiosanchezsanchez
 *
 */
public class MessageSavedAppEvent extends AbstractSseApplicationEvent {

	private static final long serialVersionUID = 1L;

	public MessageSavedAppEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

}
