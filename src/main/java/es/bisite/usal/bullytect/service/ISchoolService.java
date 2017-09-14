package es.bisite.usal.bullytect.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.bisite.usal.bullytect.dto.request.AddSchoolDTO;
import es.bisite.usal.bullytect.dto.response.SchoolDTO;

public interface ISchoolService {
	Page<SchoolDTO> findPaginated(Integer page, Integer size);
    Page<SchoolDTO> findPaginated(Pageable pageable);
    Page<SchoolDTO> findByNamePaginated(String name, Pageable pageable);
    SchoolDTO getSchoolById(String id);
    SchoolDTO save(AddSchoolDTO addSchoolDTO);
    SchoolDTO delete(String id);
}
