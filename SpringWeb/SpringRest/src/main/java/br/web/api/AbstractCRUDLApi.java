package br.web.api;

import br.web.dto.BaseDTO;
import br.web.entities.DistributedEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AbstractCRUDLApi <ENTITY extends DistributedEntity,DTO extends BaseDTO>{
    DTO save(DTO dto);
    DTO update(DTO dto);
    DTO findById(Integer id);
    void delete(Integer id);
    List<DTO> findAll();

}
