package es.bisite.usal.bulltect.persistence.entity;

import java.util.Date;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class AnalysisEntity {
	
	
	@Field("type")
	private AnalysisTypeEnum type;
	
	@Field("start_at")
    private Date startAt;
    
    @Field("finish_at")
    private Date finishAt;
    
    @Field("result")
    private Integer result;
    
    @Field("status")
    private AnalysisStatusEnum status = AnalysisStatusEnum.PENDING;
    
    
    public AnalysisEntity(){}
    
    

    public AnalysisEntity(AnalysisTypeEnum type) {
		super();
		this.type = type;
	}



	public AnalysisEntity(AnalysisTypeEnum type, AnalysisStatusEnum status) {
		super();
		this.type = type;
		this.status = status;
	}



	public AnalysisEntity(AnalysisTypeEnum type, Date startAt, AnalysisStatusEnum status) {
		super();
		this.type = type;
		this.startAt = startAt;
		this.status = status;
	}



	@PersistenceConstructor
	public AnalysisEntity(AnalysisTypeEnum type, Date startAt, Date finishAt, Integer result,
			AnalysisStatusEnum status) {
		super();
		this.type = type;
		this.startAt = startAt;
		this.finishAt = finishAt;
		this.result = result;
		this.status = status;
	}



	public AnalysisTypeEnum getType() {
		return type;
	}

	public void setType(AnalysisTypeEnum type) {
		this.type = type;
	}

	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public Date getFinishAt() {
		return finishAt;
	}

	public void setFinishAt(Date finishAt) {
		this.finishAt = finishAt;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public AnalysisStatusEnum getStatus() {
		return status;
	}

	public void setStatus(AnalysisStatusEnum status) {
		this.status = status;
	}
    

}
