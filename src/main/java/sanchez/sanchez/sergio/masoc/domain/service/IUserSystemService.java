package sanchez.sanchez.sergio.masoc.domain.service;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.masoc.web.dto.response.AdminDTO;

public interface IUserSystemService {
	AdminDTO getUserById(String id);
	AdminDTO getUserById(ObjectId id);
}
