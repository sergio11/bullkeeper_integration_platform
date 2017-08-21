package sanchez.sanchez.sergio.dto.response;

import java.io.Serializable;

public final class CommentsBySon implements Serializable {
	
	private String  firstName;
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
