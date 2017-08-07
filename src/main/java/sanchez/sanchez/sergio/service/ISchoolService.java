package sanchez.sanchez.sergio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sanchez.sanchez.sergio.dto.response.SchoolDTO;

public interface ISchoolService {
	Page<SchoolDTO> findPaginated(Integer page, Integer size);
    Page<SchoolDTO> findPaginated(Pageable pageable);
    Page<SchoolDTO> findByNamePaginated(String name, Pageable pageable);
    SchoolDTO getSchoolById(String id);
}
