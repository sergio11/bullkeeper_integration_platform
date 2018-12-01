package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

public enum AdultLevelEnum {
	NEGATIVE, POSITIVE, UNKNOWN;
	
	public static AdultLevelEnum fromResult(Integer result){
		
		AdultLevelEnum level = AdultLevelEnum.UNKNOWN; 
		
		if(result != null) {
			if(result > 0) {
				level = AdultLevelEnum.POSITIVE;
			} else {
				level = AdultLevelEnum.NEGATIVE;
			}
		}
		
		return level;
		
	}
}
