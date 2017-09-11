package sanchez.sanchez.sergio.service;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.dto.response.AdminDTO;

public interface IUserSystemService {
	AdminDTO getUserById(String id);
	AdminDTO getUserById(ObjectId id);
}
