package es.bisite.usal.bulltect.persistence.entity;

public enum AdultLevelEnum {
	UNKNOWN, POSITIVE, NEGATIVE;
	
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
