package es.bisite.usal.bulltect.persistence.entity;

public enum ViolenceLevelEnum {
	NEGATIVE, POSITIVE, UNKNOWN;
	
	public static ViolenceLevelEnum fromResult(Integer result){
		
		ViolenceLevelEnum level = ViolenceLevelEnum.UNKNOWN; 
		
		if(result != null) {
			if(result > 0) {
				level = ViolenceLevelEnum.POSITIVE;
			} else {
				level = ViolenceLevelEnum.NEGATIVE;
			}
		}
		
		return level;
		
	}
	
}
