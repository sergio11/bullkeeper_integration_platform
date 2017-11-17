package es.bisite.usal.bulltect.persistence.entity;

public enum DrugsLevelEnum {
	NEGATIVE, POSITIVE, UNKNOWN;
	
	public static DrugsLevelEnum fromResult(Integer result){
		
		DrugsLevelEnum level = DrugsLevelEnum.UNKNOWN; 
		
		if(result != null) {
			if(result > 0) {
				level = DrugsLevelEnum.POSITIVE;
			} else {
				level = DrugsLevelEnum.NEGATIVE;
			}
		}
		
		return level;
		
	}
}
