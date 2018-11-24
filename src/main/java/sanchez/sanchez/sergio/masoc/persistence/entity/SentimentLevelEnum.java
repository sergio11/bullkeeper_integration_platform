package sanchez.sanchez.sergio.masoc.persistence.entity;

/**
 * Sentiment Level Enum
 * @author sergiosanchezsanchez
 *
 */
public enum SentimentLevelEnum {
	UNKNOWN, POSITIVE, NEGATIVE, NEUTRO;
	
	public static SentimentLevelEnum fromResult(Integer result){
		
		SentimentLevelEnum sentiment = SentimentLevelEnum.UNKNOWN; 
		
		if(result != null) {
			if(result >= -10 && result <= -5) {
				sentiment = SentimentLevelEnum.NEGATIVE;
			} else if(result > -5 && result <= 5) {
				sentiment = SentimentLevelEnum.NEUTRO;
			} else {
				sentiment = SentimentLevelEnum.POSITIVE;
			}
		}
		
		return sentiment;
		
	}
}
