package es.bisite.usal.bullytect.service;

import org.bson.types.ObjectId;

import es.bisite.usal.bullytect.dto.response.AdminDTO;

public interface IUserSystemService {
	AdminDTO getUserById(String id);
	AdminDTO getUserById(ObjectId id);
}
