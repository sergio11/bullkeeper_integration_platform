package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public final class ChildrenOfGuardianDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Total
	 */
	@JsonProperty("total")
	private long total;
	
	/**
	 * No Confirmed
	 */
	@JsonProperty("no_confirmed")
	private long noConfirmed;
	
	/**
	 * Confirmed
	 */
	@JsonProperty("confirmed")
	private long confirmed;
	
	/**
	 * Supervised Children
	 */
	@JsonProperty("supervised_children")
	private Iterable<SupervisedChildrenDTO> supervisedChildren;

	
	public ChildrenOfGuardianDTO() {}
	
	
	/**
	 * 
	 * @param total
	 * @param noConfirmed
	 * @param confirmed
	 * @param supervisedChildren
	 */
	public ChildrenOfGuardianDTO(long total, long noConfirmed, long confirmed,
			Iterable<SupervisedChildrenDTO> supervisedChildren) {
		super();
		this.total = total;
		this.noConfirmed = noConfirmed;
		this.confirmed = confirmed;
		this.supervisedChildren = supervisedChildren;
	}


	public long getTotal() {
		return total;
	}


	public long getNoConfirmed() {
		return noConfirmed;
	}


	public long getConfirmed() {
		return confirmed;
	}


	public Iterable<SupervisedChildrenDTO> getSupervisedChildren() {
		return supervisedChildren;
	}


	public void setTotal(long total) {
		this.total = total;
	}


	public void setNoConfirmed(long noConfirmed) {
		this.noConfirmed = noConfirmed;
	}


	public void setConfirmed(long confirmed) {
		this.confirmed = confirmed;
	}


	public void setSupervisedChildren(Iterable<SupervisedChildrenDTO> supervisedChildren) {
		this.supervisedChildren = supervisedChildren;
	}

	
	

}
