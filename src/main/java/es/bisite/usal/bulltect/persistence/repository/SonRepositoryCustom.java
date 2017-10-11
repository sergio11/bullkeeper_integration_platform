package es.bisite.usal.bulltect.persistence.repository;

import org.bson.types.ObjectId;

public interface SonRepositoryCustom {

    void setProfileImageId(ObjectId id, String profileImageId);

    String getProfileImageIdByUserId(ObjectId id);
}
