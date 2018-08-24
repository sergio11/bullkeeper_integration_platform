package sanchez.sanchez.sergio.masoc.web.dto.response;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.masoc.persistence.entity.SocialMediaTypeEnum;

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
	@JsonProperty("comments")
	private Set<CommentDTO> comments;
	@JsonProperty("total_comments")
	private Integer totalComments;
	@JsonProperty("target")
	private SonDTO son;
	@JsonProperty("social_media_id")
	private String socialMediaId;
	@JsonProperty("social_media_type")
	private SocialMediaTypeEnum socialMediaType;
	
	public TaskDTO(){}
	
	public TaskDTO(String identity, String startDate, String finishDate, Long duration, Boolean success,
			Set<CommentDTO> comments, Integer totalComments, SonDTO son, String socialMediaId, SocialMediaTypeEnum  socialMediaType) {
		super();
		this.identity = identity;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.duration = duration;
		this.success = success;
		this.comments = comments;
		this.totalComments = totalComments;
		this.son = son;
		this.socialMediaId = socialMediaId;
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
	
	public Set<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDTO> comments) {
		this.comments = comments;
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

	public String getSocialMediaId() {
		return socialMediaId;
	}

	public void setSocialMediaId(String socialMediaId) {
		this.socialMediaId = socialMediaId;
	}

	public SocialMediaTypeEnum  getSocialMediaType() {
		return socialMediaType;
	}

	public void setSocialMediaType(SocialMediaTypeEnum  socialMediaType) {
		this.socialMediaType = socialMediaType;
	}
}
