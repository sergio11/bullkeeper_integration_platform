package es.bisite.usal.bulltect.domain.service;

import org.bson.types.ObjectId;

import es.bisite.usal.bulltect.web.dto.response.AdminDTO;

public interface IUserSystemService {
	AdminDTO getUserById(String id);
	AdminDTO getUserById(ObjectId id);
}
