package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

/**
 * Bullying Level Enum
 * @author sergiosanchezsanchez
 *
 */
public enum BullyingLevelEnum {
	
	NEGATIVE, POSITIVE, UNKNOWN;
	
	/**
	 * 
	 * @param result
	 * @return
	 */
	public static BullyingLevelEnum fromResult(Integer result){
		BullyingLevelEnum level = BullyingLevelEnum.UNKNOWN; 
		if(result != null) {
			if(result > 0) {
				level = BullyingLevelEnum.POSITIVE;
			} else {
				level = BullyingLevelEnum.NEGATIVE;
			}
		}
		return level;
	}
}
