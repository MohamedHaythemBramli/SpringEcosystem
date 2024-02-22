package br.batch.config.processor;


import br.batch.dto.SalesInfoDTO;
import br.batch.entities.SalesInfo;
import br.batch.mapper.SalesInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class SalesInfoProcessor implements ItemProcessor<SalesInfoDTO, SalesInfo> {

    private final SalesInfoMapper salesInfoMapper;
    @Override
    public SalesInfo process(SalesInfoDTO item) throws Exception {
        log.info("processing the item: {}",item.toString());
        double newPrice = item.getPrice() * 1.3;
        item.setPrice(newPrice);
        return salesInfoMapper.mapToEntity(item);
    }
}

