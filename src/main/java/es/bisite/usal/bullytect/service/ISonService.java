package es.bisite.usal.bullytect.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.bisite.usal.bullytect.dto.response.SonDTO;

public interface ISonService {
	Page<SonDTO> findPaginated(Integer page, Integer size);
    Page<SonDTO> findPaginated(Pageable pageable);
    SonDTO getSonById(String id);
    Long getTotalChildren();
    
}
