package code;

public enum RecruitStatus {
	ACTIVE("활성"),
	CLOSED("마감");
	
	RecruitStatus(String description){
		this.description = description;
	}
	private final String description;
}