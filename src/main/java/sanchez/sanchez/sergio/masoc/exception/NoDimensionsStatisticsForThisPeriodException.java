package sanchez.sanchez.sergio.masoc.exception;

import java.util.Date;

public class NoDimensionsStatisticsForThisPeriodException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final Date from;

	public NoDimensionsStatisticsForThisPeriodException(Date from) {
		super();
		this.from = from;
	}

	public Date getFrom() {
		return from;
	}

}
