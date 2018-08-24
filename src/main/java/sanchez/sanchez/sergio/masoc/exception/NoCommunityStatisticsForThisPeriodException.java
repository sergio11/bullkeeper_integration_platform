package sanchez.sanchez.sergio.masoc.exception;

import java.util.Date;

public class NoCommunityStatisticsForThisPeriodException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final Date from;
	

	public NoCommunityStatisticsForThisPeriodException(Date from) {
		super();
		this.from = from;
	}

	public Date getFrom() {
		return from;
	}
}
