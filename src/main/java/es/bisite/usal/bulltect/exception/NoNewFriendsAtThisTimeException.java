package es.bisite.usal.bulltect.exception;

import java.util.Date;

public class NoNewFriendsAtThisTimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final Date from;

	public NoNewFriendsAtThisTimeException(Date from) {
		super();
		this.from = from;
	}

	public Date getFrom() {
		return from;
	}
}
