package es.bisite.usal.bulltect.persistence.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public final class CommentAuthorEntity {
	
	@Field("external_id")
	private String externalId;
	
	@Field("name")
	private String name;
	
	@Field("image")
	private String image;
	
	public CommentAuthorEntity(){}

	@PersistenceConstructor
	public CommentAuthorEntity(String externalId, String name, String image) {
		super();
		this.externalId = externalId;
		this.name = name;
		this.image = image;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((externalId == null) ? 0 : externalId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommentAuthorEntity other = (CommentAuthorEntity) obj;
		if (externalId == null) {
			if (other.externalId != null)
				return false;
		} else if (!externalId.equals(other.externalId))
			return false;
		return true;
	}
	
	
}
