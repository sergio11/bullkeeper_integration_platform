package sanchez.sanchez.sergio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sanchez.sanchez.sergio.dto.response.SonDTO;

public interface ISonService {
	Page<SonDTO> findPaginated(Integer page, Integer size);
    Page<SonDTO> findPaginated(Pageable pageable);
    SonDTO getSonById(String id);
}
