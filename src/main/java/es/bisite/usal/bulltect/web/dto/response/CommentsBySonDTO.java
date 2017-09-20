package es.bisite.usal.bulltect.web.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CommentsBySonDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("label")
	private String fullname;
	
	@JsonProperty("value")
	private Long comments;
	

	public CommentsBySonDTO() {
		super();
	}

	public CommentsBySonDTO(String fullname, Long comments) {
		super();
		this.fullname = fullname;
		this.comments = comments;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}
}
