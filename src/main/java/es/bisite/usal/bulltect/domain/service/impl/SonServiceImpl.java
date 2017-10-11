package es.bisite.usal.bulltect.domain.service.impl;


import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.bisite.usal.bulltect.domain.service.ISonService;
import es.bisite.usal.bulltect.mapper.SonEntityMapper;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;
import es.bisite.usal.bulltect.persistence.repository.SonRepository;
import es.bisite.usal.bulltect.web.dto.response.SonDTO;
import io.jsonwebtoken.lang.Assert;
import javax.annotation.PostConstruct;

@Service
public class SonServiceImpl implements ISonService {

    private static Logger logger = LoggerFactory.getLogger(SonServiceImpl.class);

    private final SonRepository sonRepository;
    private final SonEntityMapper sonEntityMapper;

    public SonServiceImpl(SonRepository sonRepository, SonEntityMapper sonEntityMapper) {
        super();
        this.sonRepository = sonRepository;
        this.sonEntityMapper = sonEntityMapper;
    }

    @Override
    public Page<SonDTO> findPaginated(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);
        Page<SonEntity> childrenPage = sonRepository.findAll(pageable);
        return childrenPage.map(new Converter<SonEntity, SonDTO>() {
            @Override
            public SonDTO convert(SonEntity sonEntity) {
                return sonEntityMapper.sonEntityToSonDTO(sonEntity);
            }
        });
    }

    @Override
    public Page<SonDTO> findPaginated(Pageable pageable) {
        Page<SonEntity> childrenPage = sonRepository.findAll(pageable);
        return childrenPage.map(new Converter<SonEntity, SonDTO>() {
            @Override
            public SonDTO convert(SonEntity sonEntity) {
                return sonEntityMapper.sonEntityToSonDTO(sonEntity);
            }
        });
    }

    @Override
    public SonDTO getSonById(String id) {
        SonEntity sonEntity = sonRepository.findOne(new ObjectId(id));
        return sonEntityMapper.sonEntityToSonDTO(sonEntity);
    }

    @Override
    public Long getTotalChildren() {
        return sonRepository.count();
    }
    
    @Override
    public String getProfileImage(ObjectId id) {
        return sonRepository.getProfileImageIdByUserId(id);
    }
    
    @PostConstruct
    protected void init() {
        Assert.notNull(sonRepository, "Son Repository can not be null");
        Assert.notNull(sonEntityMapper, "Son Entity Mapper can not be null");
    }
}
