package es.bisite.usal.bulltect.web.rest.exception;

import java.util.Date;

public class NoActiveFriendsInThisPeriodException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final Date from;

	public NoActiveFriendsInThisPeriodException(Date from) {
		super();
		this.from = from;
	}

	public Date getFrom() {
		return from;
	}

}
