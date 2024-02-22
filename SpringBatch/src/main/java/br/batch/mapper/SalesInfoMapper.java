package br.batch.mapper;


import br.batch.dto.SalesInfoDTO;
import br.batch.entities.SalesInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SalesInfoMapper {
    SalesInfo mapToEntity(SalesInfoDTO salesInfoDTO);
}
