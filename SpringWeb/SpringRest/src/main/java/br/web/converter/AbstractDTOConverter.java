package br.web.converter;

import br.web.dto.BaseDTO;
import br.web.entities.DistributedEntity;

public abstract class AbstractDTOConverter<ENTITY extends DistributedEntity,DTO extends BaseDTO> {

    public abstract DTO convert(ENTITY entity);
    public void convert(final ENTITY entity,final DTO dto){
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
    }

}
