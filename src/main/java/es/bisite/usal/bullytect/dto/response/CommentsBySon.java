package es.bisite.usal.bullytect.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CommentsBySon implements Serializable {
	
	@JsonProperty("first_name")
	private String  firstName;
	@JsonProperty("comments")
	private Long comments;
	
	
	public CommentsBySon(){}
	
	public CommentsBySon(String firstName, Long comments) {
		super();
		this.firstName = firstName;
		this.comments = comments;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "CommentsBySon [firstName=" + firstName + ", comments=" + comments + "]";
	}
}
