package es.bisite.usal.bulltect.web.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum;

public class TaskDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("identity")
	private String identity;
	@JsonProperty("start_date")
	private String startDate;
	@JsonProperty("finish_date")
    private String finishDate;
	@JsonProperty("duration")
    private Long duration;
	@JsonProperty("is_success")
    private Boolean success = Boolean.TRUE;
	@JsonProperty("total_comments")
	private Integer totalComments;
	@JsonProperty("target")
	private SonDTO son;
	@JsonProperty("social_media_type")
	private SocialMediaTypeEnum socialMediaType;
	
	public TaskDTO(){}
	
	public TaskDTO(String identity, String startDate, String finishDate, Long duration, Boolean success,
			Integer totalComments, SonDTO son, SocialMediaTypeEnum  socialMediaType) {
		super();
		this.identity = identity;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.duration = duration;
		this.success = success;
		this.totalComments = totalComments;
		this.son = son;
		this.socialMediaType = socialMediaType;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Integer getTotalComments() {
		return totalComments;
	}

	public void setTotalComments(Integer totalComments) {
		this.totalComments = totalComments;
	}

	public SonDTO getSon() {
		return son;
	}

	public void setSon(SonDTO son) {
		this.son = son;
	}

	public SocialMediaTypeEnum  getSocialMediaType() {
		return socialMediaType;
	}

	public void setSocialMediaType(SocialMediaTypeEnum  socialMediaType) {
		this.socialMediaType = socialMediaType;
	}
}
