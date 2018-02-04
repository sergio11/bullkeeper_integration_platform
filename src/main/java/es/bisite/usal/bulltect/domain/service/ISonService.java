package es.bisite.usal.bulltect.domain.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.bisite.usal.bulltect.web.dto.response.SonDTO;
import org.bson.types.ObjectId;

public interface ISonService {
    Page<SonDTO> findPaginated(Integer page, Integer size);
    Page<SonDTO> findPaginated(Pageable pageable);
    SonDTO getSonById(String id);
    Long getTotalChildren();
    String getProfileImage(ObjectId id);
    void deleteById(String id);
    void deleteById(ObjectId id);
    void deleteAllOfParent(ObjectId id);
}
