package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public final class CommentsByKidDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * FUll Name
	 */
	@JsonProperty("label")
	private String fullname;
	
	/**
	 * Comments
	 */
	@JsonProperty("value")
	private Long comments;
	

	public CommentsByKidDTO() {
		super();
	}

	public CommentsByKidDTO(String fullname, Long comments) {
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
