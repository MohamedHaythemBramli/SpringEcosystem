package br.web.service;

import br.web.api.AbstractCRUDLApi;
import br.web.converter.AbstractDTOConverter;
import br.web.dto.BaseDTO;
import br.web.entities.DistributedEntity;
import br.web.repository.DistributedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.GenericTypeResolver;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public abstract class AbstractCRUDLService <ENTITY extends DistributedEntity,DTO extends BaseDTO> implements AbstractCRUDLApi<ENTITY,DTO> {

    private final DistributedRepository repository;
    private final Class<ENTITY> entityClass;

    private final AbstractDTOConverter<ENTITY, DTO> converter;

    public AbstractCRUDLService(DistributedRepository repository, AbstractDTOConverter<ENTITY, DTO> converter) {
        this.repository = repository;
        this.converter = converter;
        Class<?> []params = GenericTypeResolver.resolveTypeArguments(getClass(), AbstractCRUDLService.class);
        entityClass=(Class<ENTITY>) params[0];
    }

    @Override
    public DTO save(DTO dto) {
        final ENTITY entity;
        if (dto.isNew()){
            entity = initEntity();
        }
        else {
            entity= (ENTITY) repository.findById(dto.getId()).orElse(null);
        }
        if (entity==null){
            log.error("Entity not found");
            return null;
        }
        entity.setUpdatedAt(LocalDateTime.now());
        updateEntity(entity,dto);
        ENTITY savedEntity = (ENTITY) repository.save(entity);
        return converter.convert(savedEntity);
    }

    @Override
    public DTO update(DTO dto) {
        return null;
    }

    @Override
    public DTO findById(Integer id) {
        ENTITY entity = (ENTITY) repository.findById(id).orElse(null);
        if (entity==null){
            log.error("Entity not found");
            return null;
        }
        return converter.convert(entity);
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List findAll() {
        return null;
    }

    protected abstract void updateEntity(final ENTITY entity,final DTO dto);

    private ENTITY initEntity(){
        try {
            ENTITY entity = entityClass.newInstance();
            entity.setCreatedAt(LocalDateTime.now());
            return entity;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }
}
