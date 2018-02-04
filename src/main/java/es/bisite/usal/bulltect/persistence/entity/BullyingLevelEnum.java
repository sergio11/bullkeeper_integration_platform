package es.bisite.usal.bulltect.persistence.entity;

public enum BullyingLevelEnum {
	NEGATIVE, POSITIVE, UNKNOWN;
	
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
