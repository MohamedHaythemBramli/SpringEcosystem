package br.batch.config.listener;

import br.batch.dto.SalesInfoDTO;
import br.batch.entities.SalesInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;

@Component
@Slf4j
public class CustomSkipListener implements SkipListener<SalesInfoDTO, SalesInfo> {
    @Override // item reader
    public void onSkipInRead(Throwable throwable) {
        log.info("A failure on read {} ", throwable.getMessage());
    }

    @Override
    public void onSkipInWrite(SalesInfo salesInfo, Throwable t) {
        log.info("A failure on write {} , {}", t.getMessage(), salesInfo);
    }

    @SneakyThrows
    @Override// item processor
    public void onSkipInProcess(SalesInfoDTO salesInfoDTO, Throwable throwable) {
        log.info("Item {}  was skipped due to the exception  {}", new ObjectMapper().writeValueAsString(salesInfoDTO),
                throwable.getMessage());
    }

    public void createFile(String filePath, String data) {
        try(FileWriter fileWriter = new FileWriter(new File(filePath), true)) {
            fileWriter.write(data + "," + new Date() + "\n");
        }catch(Exception e) {
            throw new RuntimeException("File is not created");
        }
    }
}
