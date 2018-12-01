package sanchez.sanchez.sergio.bullkeeper.web.rest.hal;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;

import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SchoolDTO;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.SchoolController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 *
 * @author sergio
 */
public interface ISchoolHAL {
    
    default SchoolDTO addLinksToSchool(final SchoolDTO schoolResource) {
        Link selfLink = linkTo(SchoolController.class).slash(schoolResource.getIdentity()).withSelfRel();
        schoolResource.add(selfLink);
        return schoolResource;
    }

    default Iterable<SchoolDTO> addLinksToSchool(final Iterable<SchoolDTO> schoolResources) {
        for (SchoolDTO schoolResource : schoolResources) {
        	addLinksToSchool(schoolResource);
        }
        return schoolResources;
    }
    
    default Page<SchoolDTO> addLinksToSchool(final Page<SchoolDTO> schoolsPage) {
        for (SchoolDTO schoolResource : schoolsPage.getContent()) {
        	addLinksToSchool(schoolResource);
        }
        return schoolsPage;
    }
}
