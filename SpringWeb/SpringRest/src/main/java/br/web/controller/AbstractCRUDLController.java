package br.web.controller;

import br.web.api.AbstractCRUDLApi;
import br.web.dto.BaseDTO;
import br.web.entities.DistributedEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AbstractCRUDLController<ENTITY extends DistributedEntity,DTO extends BaseDTO> {
    private AbstractCRUDLApi<ENTITY,DTO> api;

    public AbstractCRUDLController(AbstractCRUDLApi<ENTITY, DTO> api) {
        this.api = api;
    }

    @PostMapping
    public void save(@RequestBody DTO dto){
        api.save(dto);
    }

    @GetMapping("/{id}")
    public DTO getById(@PathVariable Integer id) {
        return api.findById(id);
    }
}
